package com.infernaleelCheck;

import com.sun.jna.platform.win32.WinDef;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;

public class infernaleelCheckOverlay extends OverlayPanel {
    private final infernaleelCheckConfig config;
    private final Client client;
    private final infernaleelCheckPlugin plugin;
    private final String LONG_TEXT = "ICEGLOVES CP";
    private final String SHORT_TEXT = "ICEGLOVES";

    @Inject
    private infernaleelCheckOverlay(infernaleelCheckConfig config, Client client, infernaleelCheckPlugin plugin) {
        this.config = config;
        this.client = client;
        this.plugin = plugin;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if (panelComponent == null) {
            return null;
        }
        panelComponent.getChildren().clear();
        switch (config.reminderStyle()) {
            case LONG_TEXT:
                panelComponent.getChildren().add((LineComponent.builder())
                        .left(LONG_TEXT)
                        .build());
                panelComponent.setPreferredSize(new Dimension(graphics.getFontMetrics().stringWidth(LONG_TEXT) - 20, 0));
                break;
            case SHORT_TEXT:
                panelComponent.getChildren().add((LineComponent.builder())
                        .left(SHORT_TEXT)
                        .build());
                panelComponent.setPreferredSize(new Dimension(graphics.getFontMetrics().stringWidth(SHORT_TEXT) + 6, 0));
                break;


        }

        if (config.shouldFlash()) {
            if (client.getGameCycle() % 40 >= 20) {
                panelComponent.setBackgroundColor(config.flashColor1());
            } else {
                panelComponent.setBackgroundColor(config.flashColor2());
            }
        } else {
            panelComponent.setBackgroundColor(config.flashColor1());
        }
        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
        return panelComponent.render(graphics);
    }
}
