package com.ihfazh.ulearnoop.elevator.fp

import com.ihfazh.ulearnoop.elevator.commands.ElevatorCommand
import java.util.*


data class EntityId(val uuid: UUID){
    companion object {
        fun mint() = EntityId(UUID.randomUUID())

    }
}

interface EntityEvent {
    val id: EntityId
}


// TODO: use it when already use event. For now
// just insert "command", "state" as param and will return a new state
//interface EntityState<in E: EntityEvent> {
//    fun combine(event: E) : EntityState<E>
//}

interface  EntityState{
    fun combine(command: ElevatorCommand): EntityState?
}