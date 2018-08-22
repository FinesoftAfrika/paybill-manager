package com.thomaskioko.paybillmanager.data.test.store

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.paybillmanager.data.repository.BillsCache
import com.thomaskioko.paybillmanager.data.store.BillsCacheDataStore
import com.thomaskioko.paybillmanager.data.test.factory.BillsDataFactory
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BillCacheDataStoreTest {

    private val cache = mock<BillsCache>()
    private val store = BillsCacheDataStore(cache)

    @Test
    fun getBillsCompletes() {

        val observable = Flowable.just(BillsDataFactory.makeBillEntityList(2))

        //Stub getBills call
        whenever(cache.getBills()).thenReturn(observable)

        val testObserver = store.getBills().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBillsReturnData() {
        val data = listOf(BillsDataFactory.makeBillEntity())

        whenever(cache.getBills()).thenReturn(Flowable.just(data))

        //Create test observer
        val testObserver = store.getBills().test()
        //confirm that the observer completes
        testObserver.assertValue(data)
    }

    @Test
    fun getBillsCallsCacheStore() {
        val data = listOf(BillsDataFactory.makeBillEntity())

        //Stub get bills
        whenever(cache.getBills()).thenReturn(Flowable.just(data))

        //Invoke call
        store.getBills().test()
        //Verify that the right method is called
        verify(cache).getBills()
    }

    @Test
    fun createBillCompletes() {

        //Stub create bill
        whenever(cache.createBill(any())).thenReturn(Completable.complete())

        val data = BillsDataFactory.makeBillEntity()

        val testObserver = store.createBill(data).test()
        //confirm that the observer completes
        testObserver.assertComplete()
    }

    @Test
    fun updateBillCompletes() {
        val data = BillsDataFactory.makeBillEntity()

        //Stub updateBill
        whenever(cache.updateBill(any())).thenReturn(Completable.complete())

        val testObserver = store.updateBill(data).test()
        //confirm that the observer completes
        testObserver.assertComplete()
    }

    @Test
    fun deleteBillCompletes() {

        //Stub updateBill
        whenever(cache.deleteBills()).thenReturn(Completable.complete())

        val testObserver = store.deleteBills().test()
        //confirm that the observer completes
        testObserver.assertComplete()
    }


}