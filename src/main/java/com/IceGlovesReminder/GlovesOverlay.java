package com.IceGlovesReminder;

import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;

public class GlovesOverlay extends OverlayPanel {
    private final GlovesConfig config;
    private final Client client;
    private final GlovesPlugin plugin;
    private final String LONG_TEXT = "EQUIP ICE GLOVES";
    private final String SHORT_TEXT = "ICE GLOVES";

    @Inject
    private GlovesOverlay(GlovesConfig config, Client client, GlovesPlugin plugin) {
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
        panelComponent.setBackgroundColor(config.color());
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
                panelComponent.setPreferredSize(new Dimension(graphics.getFontMetrics().stringWidth(SHORT_TEXT) + 8, 0));
                break;


        }
        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
        return panelComponent.render(graphics);
    }
}
