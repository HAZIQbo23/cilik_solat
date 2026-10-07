package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.SolatDataRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Cilik Solat", appName)
    }

    @Test
    fun `verify Islamic curriculum data`() {
        assertEquals(8, SolatDataRepository.wudukSteps.size)
        assertEquals(13, SolatDataRepository.rukunSolatList.size)
        assertEquals(11, SolatDataRepository.bacaanSolatList.size)
        assertEquals(5, SolatDataRepository.solatWaktuList.size)
        assertTrue(SolatDataRepository.quizQuestions.isNotEmpty())
    }
}
