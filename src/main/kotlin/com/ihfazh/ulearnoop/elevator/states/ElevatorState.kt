package com.ihfazh.ulearnoop.elevator.states

import com.ihfazh.ulearnoop.elevator.commands.Direction
import com.ihfazh.ulearnoop.elevator.commands.ElevatorCommand
import com.ihfazh.ulearnoop.elevator.commands.Open
import com.ihfazh.ulearnoop.elevator.commands.Travel
import com.ihfazh.ulearnoop.elevator.fp.EntityState

/*
States:
- idling [closed, when no one used this elevator]
- opening
- traveling [closed, someone used it to travel from one floor to another]
 */

sealed class ElevatorState: EntityState{
    abstract override fun combine(command: ElevatorCommand): EntityState?
}



data class IdlingElevator internal constructor(
    val floor: Int
): ElevatorState(){
    override fun combine(command: ElevatorCommand): EntityState? {
        // yang paling bawah adalah 1, paling atas tidak terhingga
        return when(command){
            is Open -> open(floor, command.direction)
            else -> this
        }
    }
}


data class OpeningElevator internal constructor(
    val floor: Int
): ElevatorState(){
    override fun combine(command: ElevatorCommand): EntityState {
        return when(command){
            is Travel -> travel(floor, command.toFloor)
            else -> this
        }
    }
}


data class TravelingElevator internal constructor(
    val floor: Int,
    val toFloor: Int
): ElevatorState(){
    override fun combine(command: ElevatorCommand): EntityState {
        return this
    }
}


fun IdlingElevator.open(floor: Int, direction: Direction) : EntityState? {
    if (floor == 1 && direction == Direction.DOWN){
        return this
    }

    return OpeningElevator(floor)
}


fun OpeningElevator.travel(fromFloor: Int, toFloor: Int): EntityState {
    if (fromFloor == 1 && fromFloor > toFloor) {
        return IdlingElevator(fromFloor)
    }

    return if (fromFloor > toFloor) {
        IdlingElevator(fromFloor)
    } else {
        TravelingElevator(fromFloor, toFloor)
    }
}