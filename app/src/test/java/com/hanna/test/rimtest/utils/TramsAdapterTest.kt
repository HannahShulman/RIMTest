package com.hanna.test.rimtest.utils

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import com.hanna.test.rimtest.R
import com.hanna.test.rimtest.entities.Tram
import com.hanna.test.rimtest.ui.TramViewHolder
import com.hanna.test.rimtest.ui.TramsAdapter
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class TramsAdapterTest {

    private val adapter = TramsAdapter().apply {
        submitList(
            listOf(
                Tram("destination", "1"),
                Tram("destination", "67"),
                Tram("destination", ""),
            )
        )
    }

    private lateinit var context: Context

    private val layout: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.single_tram_info, null)
    }

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }


    @Test
    fun `bind view holder, with tram due in minutes,  sets correct text`() {
        val holder = TramViewHolder(layout)
        adapter.bindViewHolder(holder, 0)
        holder.bindData(adapter.currentList[0])
        assertThat(holder.destinationTv.text).isEqualTo("destination")
        assertThat(holder.dueMinsTv.text).isEqualTo("1m")
    }

    @Test
    fun `bind view holder, with tram due in above hour,  sets correct text`() {
        val holder = TramViewHolder(layout)
        adapter.bindViewHolder(holder, 1)
        holder.bindData(adapter.currentList[1])
        assertThat(holder.destinationTv.text).isEqualTo("destination")
        assertThat(holder.dueMinsTv.text).isEqualTo("1h:7m")
    }

    @Test
    fun `bind view holder, no info, when due, sets correct text`() {
        val holder = TramViewHolder(layout)
        adapter.bindViewHolder(holder, 2)
        holder.bindData(adapter.currentList[2])
        assertThat(holder.destinationTv.text).isEqualTo("destination")
        assertThat(holder.dueMinsTv.text).isEqualTo("---")
    }

}