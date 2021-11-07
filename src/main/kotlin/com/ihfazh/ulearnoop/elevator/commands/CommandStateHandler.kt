package com.ihfazh.ulearnoop.elevator.commands

import com.ihfazh.ulearnoop.elevator.fp.EntityState
import com.ihfazh.ulearnoop.elevator.states.ElevatorState

class CommandStateHandler(
    private val state: ElevatorState
): (ElevatorCommand) -> EntityState? {
    override fun invoke(command: ElevatorCommand): EntityState? {
        return state.combine(command)
    }
}