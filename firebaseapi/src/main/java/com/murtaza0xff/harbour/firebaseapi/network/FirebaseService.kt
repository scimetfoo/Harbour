package com.murtaza0xff.harbour.firebaseapi.network

import androidx.annotation.VisibleForTesting
import com.google.firebase.database.*
import com.murtaza0xff.harbour.firebaseapi.models.HackerNewsItem
import com.murtaza0xff.harbour.firebaseapi.models.SealedStory
import com.squareup.moshi.Moshi
import io.reactivex.*
import javax.inject.Inject


class FirebaseService @Inject constructor(private val firebaseDatabase: FirebaseDatabase, private val moshi: Moshi) {

    fun fetchItemIds(sealedStory: SealedStory, page: Int, itemsPerPage: Long = 25): Flowable<Long> {
        return getSelectedFeed(sealedStory)
            .flattenAsObservable {
                it.children
            }
            .skip(page.plus(1).times(itemsPerPage).minus(itemsPerPage))
            .take(itemsPerPage)
            .map { it.value as Long }
            .toFlowable(BackpressureStrategy.LATEST)
    }

    fun fetchChildrenId(ids: List<Long>): Flowable<Long> {
        return Flowable
            .fromIterable(ids)
    }

    fun fetchHnItemFromId(id: Flowable<Long>): Flowable<HackerNewsItem> {
        return id.concatMapEager {
                observeItem(firebaseDatabase.getReference("v0/item/$it"))
            }
            .map(HackerNewsItem.Companion::create)
    }

    private fun getSelectedFeed(sealedStory: SealedStory): Single<DataSnapshot> = observeRoute(
        firebaseDatabase.getReference(sealedStory.route)
    )

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun observeRoute(databaseReference: DatabaseReference): Single<DataSnapshot> {
        return Single.create<DataSnapshot> {
            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    it.onSuccess(dataSnapshot)
                }

                override fun onCancelled(error: DatabaseError) {
                    it.onError(error.toException())
                }
            }
            it.setCancellable {
                databaseReference.removeEventListener(valueEventListener)
            }
            databaseReference.addValueEventListener(valueEventListener)
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun observeItem(databaseReference: DatabaseReference): Flowable<DataSnapshot> {
        return Flowable.create<DataSnapshot>({
            val listener = object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    it.onError(error.toException())
                }

                override fun onDataChange(data: DataSnapshot) {
                    it.onNext(data)
                    it.onComplete()
                }
            }
            it.setCancellable {
                databaseReference.removeEventListener(listener)
            }
            databaseReference.addValueEventListener(listener)
        }, BackpressureStrategy.LATEST)
    }
}