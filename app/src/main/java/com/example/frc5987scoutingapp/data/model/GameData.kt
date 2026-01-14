package com.example.frc5987scoutingapp.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.frc5987scoutingapp.data.model.enums.CagePosition
import com.example.frc5987scoutingapp.data.model.enums.Cards
import com.example.frc5987scoutingapp.data.model.enums.EndPosition
import com.example.frc5987scoutingapp.data.model.enums.PickUpOptions
import com.example.frc5987scoutingapp.data.model.enums.RobotAlliance
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
    val id: Int = 0,

    //PreMatch
    val scouter: String = "",
    val matchNumber: Int = 0,
    val robotAlliance: RobotAlliance = RobotAlliance.None,
    val teamNumber: Int = 0,
    val startingPosition: robotPosition = robotPosition.None,
    val noShow: Boolean = false,
    val cagePosition: CagePosition = CagePosition.None,

    //Autonomous
    val moved: Boolean = false,
    val timer: Double = 0.0,
    val a_l1Scored: Int = 0,
    val a_l2Scored: Int = 0,
    val a_l3Scored: Int = 0,
    val a_l4Scored: Int = 0,
    val a_bargeAlgae: Int = 0,
    val a_processorAlgae: Int = 0,
    val a_removedAlgae: Boolean = false,
    val a_foul: Int = 0,

    //teleop
    val t_removedAlgae: Boolean = false,
    val pickUpOptions : PickUpOptions = PickUpOptions._1,
    val t_l1Scored: Int = 0,
    val t_l2Scored: Int = 0,
    val t_l3Scored: Int = 0,
    val t_l4Scored: Int = 0,
    val t_bargeAlgae: Int = 0,
    val t_processorAlgae: Int = 0,
    val t_didDefance: Boolean = false,
    val t_wasDefended: Boolean = false,
    val t_foul: Int = 0,
    val t_touchedOpposingCage: Int = 0,


    //EndGame
    val endPosition: EndPosition = EndPosition.No,
    val died: Boolean = false,
    val fellOver: Boolean = false,

    //PostMatch
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
                cagePosition = enumValueOf(array[6], CagePosition.None),
                moved = array[7].toBoolean(),
                timer = array[8].toDouble(),
                a_l1Scored = array[9].toInt(),
                a_l2Scored = array[10].toInt(),
                a_l3Scored = array[11].toInt(),
                a_l4Scored = array[12].toInt(),
                a_bargeAlgae = array[13].toInt(),
                a_processorAlgae = array[14].toInt(),
                a_removedAlgae = array[15].toBoolean(),
                a_foul = array[16].toInt(),
                t_removedAlgae = array[17].toBoolean(),
                pickUpOptions = enumValueOf("_" + array[18], PickUpOptions._1),
                t_l1Scored = array[19].toInt(),
                t_l2Scored = array[20].toInt(),
                t_l3Scored = array[21].toInt(),
                t_l4Scored = array[22].toInt(),
                t_bargeAlgae = array[23].toInt(),
                t_processorAlgae = array[24].toInt(),
                t_didDefance = array[25].toBoolean(),
                t_wasDefended = array[26].toBoolean(),
                t_touchedOpposingCage  = array[27].toInt(),
                endPosition = enumValueOf(array[28], EndPosition.No),
                died = array[29].toBoolean(),
                fellOver = array[30].toBoolean(),
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
