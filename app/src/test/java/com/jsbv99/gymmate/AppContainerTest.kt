package com.jsbv99.gymmate.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import org.junit.Assert.assertSame
import org.junit.Test

class AppContainerTest {
    private class Dependency
    private class SampleViewModel(val dependency: Dependency) : ViewModel()

    @Test
    fun factoryInjectsTheProvidedDependency() {
        val dependency = Dependency()
        val factory = AppContainer().factory { SampleViewModel(dependency) }

        assertSame(dependency, factory.create(SampleViewModel::class.java, CreationExtras.Empty).dependency)
    }
}
