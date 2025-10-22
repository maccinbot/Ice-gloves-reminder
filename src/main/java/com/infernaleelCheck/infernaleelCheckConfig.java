package com.infernaleelCheck;

import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import java.awt.*;

@ConfigGroup("infernaleelCheck")
public interface infernaleelCheckConfig extends Config
{
    @ConfigItem(
            keyName = "checkAnglerEquipped",
            name = "Angler equipped",
            description = "Toggle if you want the reminder only when angler or spirit angler equipped",
            position = 1
    )
    default boolean anglerEquipped(){
        return false;
    }

    default boolean spiritAnglerEquipped(){
        return false;
    }
    @Alpha
    @ConfigItem(
            keyName = "flashColor1",
            name = "Main Colour",
            description = "pick main colour of the popup reminder",
            position = 4
    )
    default Color flashColor1(){
        return new Color(23, 255, 177, 150);
    }

    @ConfigItem(
            keyName = "shouldFlash",
            name = "Disco mode",
            description = "make the popup box flash two colors",
            position = 3
    )
    default boolean shouldFlash(){
        return false;
    }

    @Alpha
    @ConfigItem(
            keyName = "flashColor2",
            name = "Secondary Colour",
            description = "pick secondary color colour of the popup reminder",
            position = 5
    )
    default Color flashColor2(){
        return new Color(151, 88, 221, 150);
    }

    @ConfigItem(
            keyName = "ReminderStyle",
            name = "Reminder Style",
            description = "Changes the style of the reminder overlay.",
            position = 6
    )
    default infernaleelCheckStyle reminderStyle(){
        return infernaleelCheckStyle.LONG_TEXT;
    }

}

