package com.ihfazh.ulearnoop.elevator.commands


enum class Direction{
    UP,
    DOWN
}

sealed class ElevatorCommand

data class Open(val direction: Direction): ElevatorCommand()

object Close : ElevatorCommand()

data class Travel(val toFloor: Int): ElevatorCommand()
