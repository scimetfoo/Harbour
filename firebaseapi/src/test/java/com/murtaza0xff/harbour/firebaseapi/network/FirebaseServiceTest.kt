package com.murtaza0xff.harbour.firebaseapi.network

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.moshi.Moshi
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class FirebaseServiceTest {

    init {
        MockKAnnotations.init(this)
    }

    @MockK
    lateinit var firebaseDatabase: FirebaseDatabase
    @MockK
    lateinit var moshi: Moshi
    @RelaxedMockK
    lateinit var databaseReference: DatabaseReference
    @MockK
    lateinit var dataSnapshot: DataSnapshot

    private lateinit var service: FirebaseService

    @Test
    @DisplayName("When page 0 is requested with elements per page = 25, should return the first 25 posts")
    fun retrieveFirst25Posts_givenPageNumber0AndItemsPerPage25() {
        service = FirebaseService(firebaseDatabase, moshi)

        every { firebaseDatabase.getReference(any()) } returns databaseReference
        every { databaseReference.removeEventListener(any<ValueEventListener>()) } answers { }

        val observer = Flowable.create<DataSnapshot>({
            it.onNext(dataSnapshot)
        }, BackpressureStrategy.LATEST)
            .map {
                it.value as Long
            }
            .concatMapEager {
                service.observeItem(databaseReference)
            }.test()

        databaseReference.setValue(1)
        databaseReference.setValue(1)
        databaseReference.setValue(1)
        databaseReference.setValue(1)
        databaseReference.setValue(1)


        observer.assertValueCount(5)
    }
}