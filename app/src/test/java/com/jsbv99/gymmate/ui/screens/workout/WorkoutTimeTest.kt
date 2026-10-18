package com.jsbv99.gymmate.ui.screens.workout

import org.junit.Assert.assertEquals
import org.junit.Test

class WorkoutTimeTest {
    @Test fun formatsDurationWithoutWrappingHoursOrShowingNegativeTime() {
        assertEquals("00:00", workoutTime(-1))
        assertEquals("00:59", workoutTime(59))
        assertEquals("01:00", workoutTime(60))
        assertEquals("60:01", workoutTime(3601))
    }
}
