package com.IceGlovesReminder;

import net.runelite.client.config.Alpha;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import java.awt.*;

@ConfigGroup("IceGloves")
public interface GlovesConfig extends Config
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

    @Alpha
    @ConfigItem(
            keyName = "Color",
            name = "Color",
            description = "pick main color of the popup reminder",
            position = 4
    )
    default Color color(){
        return new Color(23, 255, 177, 150);
    }

    @ConfigItem(
            keyName = "ReminderStyle",
            name = "Reminder Style",
            description = "Changes the style of the reminder overlay.",
            position = 6
    )
    default GlovesStyle reminderStyle(){
        return GlovesStyle.LONG_TEXT;
    }

}

