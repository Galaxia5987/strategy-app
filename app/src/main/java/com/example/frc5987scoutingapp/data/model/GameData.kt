package com.example.frc5987scoutingapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.frc5987scoutingapp.data.model.enums.Cards
import com.example.frc5987scoutingapp.data.model.enums.EndPosition
import com.example.frc5987scoutingapp.data.model.enums.PickUpOptions
import com.example.frc5987scoutingapp.data.model.enums.RobotAlliance
import com.example.frc5987scoutingapp.data.model.enums.ClimbedTower
import com.example.frc5987scoutingapp.data.model.enums.robotPosition

@Entity(
    tableName = "gameData",
    //: הגדרת המפתח הזר שמקשר לטבלת Team
    foreignKeys = [ForeignKey(
        entity = teams::class,
        parentColumns = ["teamNumber"],
        childColumns = ["teamNumber"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class GameData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    //PreMatch
    val scouter: String = "",
    val matchNumber: Int = 0,
    val robotAlliance: RobotAlliance = RobotAlliance.None,
    val teamNumber: Int = 0,
    val startingPosition: robotPosition = robotPosition.None,
    val noShow: Boolean = false,

    //Autonomous
    val a_moved: Boolean = false,
    //val timer: Double = 0.0,
    val a_robotFoulScored: Int = 0,
    val a_robotFoulMissed: Int = 0,
    val a_humanFoulScored: Int = 0,
    val a_humanFoulMissed: Int = 0,
    val a_didThrowFoulToAllianceSide: Boolean = false,
    val a_climbedTower: ClimbedTower = ClimbedTower.No,
    val autoFoul: Int = 0,

    //teleop
    val fuelCollectedFromGround: Boolean = false,
    val fuelCollectedFromPlayer: Boolean = false,
    val fuelCollectedFromDepot: Boolean = false,
    val t_robotFoulScored: Int = 0,
    val t_robotFoulMissed: Int = 0,
    val t_humanFoulScored: Int = 0,
    val t_humanFoulMissed: Int = 0,
    val t_crossedField: Boolean = false,
    val t_didDefance: Boolean = false,
    val t_didPIN: Boolean = false,
    val t_recivedPINFoul: Boolean = false,
    val t_wasDefended: Boolean = false,
    val t_touchedOpposingTower: Int = 0,


    //EndGame
    val e_climbedTower: ClimbedTower = ClimbedTower.No,
    val e_throFuolDuringClimb: Boolean = false,
    val e_tippedOrFellOver: Boolean = false,

    //PostMatch
    val died: Boolean = false,
    val offenseSkills: Int = 0,
    val defenseSkills: Int = 0,
    val cards: Cards = Cards.No_Card,
    val comments: String = ""
    ){
    companion object {
        fun createFromArray(array: Array<String>) =
            GameData(
                id = 0,
                scouter = array[0],
                matchNumber = array[1].toInt(),
                robotAlliance = enumValueOf(array[2], RobotAlliance.None),
                teamNumber = array[3].toInt(),
                startingPosition = enumValueOf(array[4], robotPosition.None),
                noShow = array[5].toBoolean(),
                a_moved = array[6].toBoolean(),
                a_robotFoulScored = array[7].toInt(),
                a_robotFoulMissed = array[8].toInt(),
                a_humanFoulScored = array[9].toInt(),
                a_humanFoulMissed = array[10].toInt(),
                a_didThrowFoulToAllianceSide = array[11].toBoolean(),
                a_climbedTower = enumValueOf(array[12], ClimbedTower.No),
                autoFoul = array[13].toInt(),
                fuelCollectedFromGround = array[14].toBoolean(),
                fuelCollectedFromPlayer = array[15].toBoolean(),
                fuelCollectedFromDepot = array[16].toBoolean(),
                t_robotFoulScored = array[17].toInt(),
                t_robotFoulMissed = array[18].toInt(),
                t_humanFoulScored = array[19].toInt(),
                t_humanFoulMissed = array[20].toInt(),
                t_crossedField = array[21].toBoolean(),
                t_didDefance = array[22].toBoolean(),
                t_didPIN = array[23].toBoolean(),
                t_recivedPINFoul = array[24].toBoolean(),
                t_wasDefended = array[25].toBoolean(),
                t_touchedOpposingTower = array[26].toInt(),
                e_climbedTower = enumValueOf(array[27], ClimbedTower.No),
                e_throFuolDuringClimb = array[28].toBoolean(),
                e_tippedOrFellOver = array[29].toBoolean(),
                died = array[30].toBoolean(),
                offenseSkills = array[31].toInt(),
                defenseSkills = array[32].toInt(),
                cards = enumValueOf(array[33].replace(" ", "_"), Cards.No_Card),
                comments = array[34]
            )

        private inline fun <reified T : Enum<T>> enumValueOf(name: String, default: T): T {
            return try {
                java.lang.Enum.valueOf(T::class.java, name)
            } catch (e: IllegalArgumentException) {
                default
            }
        }
    }
}
