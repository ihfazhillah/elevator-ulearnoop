package com.ihfazh.ulearnoop.elevator.commands

import com.ihfazh.ulearnoop.elevator.states.IdlingElevator
import com.ihfazh.ulearnoop.elevator.states.OpeningElevator
import com.ihfazh.ulearnoop.elevator.states.TravelingElevator
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

internal class CommandStateHandlerTest {
    @Test
    fun `Initial state only can do open with direction up`(){
        val handler = CommandStateHandler(IdlingElevator(1))
        val nextState = handler(Open(Direction.UP))
        expectThat(nextState).isEqualTo(OpeningElevator(1))

        expectThat(handler(Open(Direction.DOWN))).isEqualTo(IdlingElevator(1))
        expectThat(handler(Close)).isEqualTo(IdlingElevator(1))
    }

    @Test
    fun `idling elevator from up to down`(){
        val handler = CommandStateHandler(IdlingElevator(3)) // expected floors 4
        expectThat(handler(Open(Direction.UP))).isEqualTo(OpeningElevator(3))
        expectThat(handler(Open(Direction.DOWN))).isEqualTo(OpeningElevator(3))
    }

    @Test
    fun `traveling elevator`(){
        val handler = CommandStateHandler(OpeningElevator(1))
        val nextState = handler(Travel(2))
        expectThat(nextState).isEqualTo(TravelingElevator(1, 2))
        expectThat(handler(Travel(0))).isEqualTo(IdlingElevator(1))
    }

    @Test
    fun `traveling elevator cannot be changed`(){
        val handler = CommandStateHandler(TravelingElevator(1, 2))
        expectThat(handler(Open(Direction.UP))).isEqualTo(TravelingElevator(1, 2))
    }
}