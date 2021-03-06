package com.murtaza0xff.harbour.firebaseapi.network

import androidx.annotation.VisibleForTesting
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.murtaza0xff.harbour.firebaseapi.models.HackerNewsItem
import com.murtaza0xff.harbour.firebaseapi.models.SortOptions
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject


class FirebaseService @Inject constructor(private val firebaseDatabase: FirebaseDatabase) {

    /**
     * Fetch the HN items from the provided either topstories, beststories, or hotstories
     */
    fun fetchItemIds(sortOptions: SortOptions, page: Int, itemsPerPage: Long = 25): Flowable<Long> {
        return getSelectedFeed(sortOptions)
            .flattenAsFlowable {
                it.children
            }
            .skip(page.plus(1).times(itemsPerPage).minus(itemsPerPage))
            .take(itemsPerPage)
            .map { it.value as Long }
    }

    fun fetchChildrenId(ids: List<Long>): Flowable<Long> {
        return Flowable
            .fromIterable(ids)
    }

    /**
     * From the IDs retrieved, retrieve the content of each ID.
     */
    fun fetchHnItemFromId(id: Long): Flowable<HackerNewsItem> {
        return Flowable
            .just(id)
            .concatMapEager { observeHnItem(firebaseDatabase.getReference("v0/item/$it")) }
            .map(HackerNewsItem.Companion::create)
    }

    private fun getSelectedFeed(sortOptions: SortOptions): Single<DataSnapshot> =
        observeHnRoute(firebaseDatabase.getReference(sortOptions.route))

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun observeHnRoute(databaseReference: DatabaseReference): Single<DataSnapshot> {

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

    private fun observeHnItem(databaseReference: DatabaseReference): Flowable<DataSnapshot> {
        return Flowable.create<DataSnapshot>({
            val listener = object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    it.onError(error.toException())
                }

                override fun onDataChange(data: DataSnapshot) {
                    it.onNext(data)
                }
            }
            it.setCancellable {
                databaseReference.removeEventListener(listener)
            }
            databaseReference.addValueEventListener(listener)
        }, BackpressureStrategy.LATEST)
    }
}