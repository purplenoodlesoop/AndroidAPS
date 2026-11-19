package app.aaps.pump.medtronic

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

/**
 * The pump does not report bolus progress, so the driver counts it from the time since the pump
 * accepted the command. These are the numbers the progress display and the busy time are made from.
 */
class MedtronicBolusEstimateUTest {

    @Test
    fun `a bolus takes 42 seconds per unit`() {
        assertThat(MedtronicPumpPlugin.estimatedBolusSeconds(1.0)).isEqualTo(42)
        assertThat(MedtronicPumpPlugin.estimatedBolusSeconds(2.5)).isEqualTo(105)
        assertThat(MedtronicPumpPlugin.estimatedBolusSeconds(0.1)).isEqualTo(4)
    }

    @Test
    fun `nothing is delivered at the start`() {
        assertThat(MedtronicPumpPlugin.estimatedBolusDelivered(4.0, 0, 0.1)).isWithin(1e-9).of(0.0)
    }

    @Test
    fun `the delivered part grows with time and is rounded down to the pump step`() {
        // 4 U takes 168 s: after 84 s half is delivered, after 100 s 2.38 U, shown as 2.3 U
        assertThat(MedtronicPumpPlugin.estimatedBolusDelivered(4.0, 84, 0.1)).isWithin(1e-9).of(2.0)
        assertThat(MedtronicPumpPlugin.estimatedBolusDelivered(4.0, 100, 0.1)).isWithin(1e-9).of(2.3)
    }

    @Test
    fun `the whole bolus is delivered at the end and never more`() {
        assertThat(MedtronicPumpPlugin.estimatedBolusDelivered(4.0, 168, 0.1)).isWithin(1e-9).of(4.0)
        assertThat(MedtronicPumpPlugin.estimatedBolusDelivered(4.0, 500, 0.1)).isWithin(1e-9).of(4.0)
    }

    @Test
    fun `a bolus too small to take a second counts as delivered`() {
        assertThat(MedtronicPumpPlugin.estimatedBolusDelivered(0.0, 0, 0.1)).isWithin(1e-9).of(0.0)
    }
}
