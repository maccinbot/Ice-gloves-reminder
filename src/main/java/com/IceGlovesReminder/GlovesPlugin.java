package com.IceGlovesReminder;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameTick;
import net.runelite.api.gameval.InventoryID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemVariationMapping;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.api.gameval.ItemID;
import net.runelite.client.ui.overlay.OverlayManager;

import java.time.Instant;

@Slf4j
@PluginDescriptor(
	name = "Ice Gloves Reminder"
)
public class GlovesPlugin extends Plugin{

    private int overlayVisible;

    private static final int OILY_FISHING_ROD = ItemID.OILY_FISHING_ROD;
    private static final int FISHING_BAIT = ItemID.FISHING_BAIT;
    private static final int HAMMER = ItemID.HAMMER;
    private static final int ICE_GLOVES = ItemID.ICE_GLOVES;
    private static final int ANGLER_HAT = 13258;
    private static final int ANGLER_TOP = 13259;
    private static final int ANGLER_LEGS = 13260;
    private static final int ANGLER_BOOTS = 13261;
    private static final int SPIRIT_ANGLER_HAT = ItemID.SPIRIT_ANGLER_HAT;
    private static final int SPIRIT_ANGLER_TOP = ItemID.SPIRIT_ANGLER_TOP;
    private static final int SPIRIT_ANGLER_LEGS = ItemID.SPIRIT_ANGLER_LEGS;
    private static final int SPIRIT_ANGLER_BOOTS = ItemID.SPIRIT_ANGLER_BOOTS;

	@Inject
	private Client client;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private GlovesOverlay glovesOverlay;

	@Inject
	private GlovesConfig config;

    @Provides
    GlovesConfig provideConfig(ConfigManager configManager){
        return configManager.getConfig(GlovesConfig.class);
    }

    boolean checkGloves(){
        final ItemContainer equipment = client.getItemContainer(InventoryID.WORN);
        if (equipment == null) {
            return false;
        }
        return equipment.contains(ICE_GLOVES);
    }

    boolean checkInventory() {
        final ItemContainer inventory = client.getItemContainer(InventoryID.INV);
        if (inventory == null) {
            return false;
        }
        return inventory.contains(ICE_GLOVES);
    }

    boolean checkHelm(){
        ItemContainer equipment = client.getItemContainer(InventoryID.WORN);
        Item helm = equipment != null ? equipment.getItem(EquipmentInventorySlot.HEAD.getSlotIdx()) : null;
        if (helm == null) {
            return false;
        }
        boolean helmetEquipped = ItemVariationMapping.getVariations(ANGLER_HAT).contains(helm.getId());
        boolean helmetEquipped2 = ItemVariationMapping.getVariations(SPIRIT_ANGLER_HAT).contains(helm.getId());
        return helmetEquipped || helmetEquipped2;
    }

    boolean checkTop(){
        ItemContainer equipment = client.getItemContainer(InventoryID.WORN);
        Item top = equipment != null ? equipment.getItem(EquipmentInventorySlot.BODY.getSlotIdx()) : null;
        if (top == null) {
            return false;
        }
        boolean topEquipped = ItemVariationMapping.getVariations(ANGLER_TOP).contains(top.getId());
        boolean topEquipped2 = ItemVariationMapping.getVariations(SPIRIT_ANGLER_TOP).contains(top.getId());
        return topEquipped || topEquipped2;
    }

    boolean checkLegs(){
        ItemContainer equipment = client.getItemContainer(InventoryID.WORN);
        Item legs = equipment != null ? equipment.getItem(EquipmentInventorySlot.LEGS.getSlotIdx()) : null;
        if (legs == null) {
            return false;
        }
        boolean legsEquipped = ItemVariationMapping.getVariations(ANGLER_LEGS).contains(legs.getId());
        boolean legsEquipped2 = ItemVariationMapping.getVariations(SPIRIT_ANGLER_LEGS).contains(legs.getId());
        return  legsEquipped || legsEquipped2;
    }

    boolean checkBoots(){
        ItemContainer equipment = client.getItemContainer(InventoryID.WORN);
        Item boots = equipment != null ? equipment.getItem(EquipmentInventorySlot.BOOTS.getSlotIdx()) : null;
        if (boots == null) {
            return false;
        }
        boolean bootsEquipped = ItemVariationMapping.getVariations(ANGLER_BOOTS).contains(boots.getId());
        boolean bootsEquipped2 = ItemVariationMapping.getVariations(SPIRIT_ANGLER_BOOTS).contains(boots.getId());
        return bootsEquipped || bootsEquipped2;
    }

    boolean checkFullAngler(){
        return checkLegs() && checkHelm() && checkBoots() && checkTop();
    }

    boolean checkItemsInInventory(){
        ItemContainer inventory = client.getItemContainer(InventoryID.INV);
        if (inventory == null) {
            return false;
        }
        boolean fishingBait =  inventory.contains(FISHING_BAIT);
        boolean oilyFishingRod = inventory.contains(OILY_FISHING_ROD);
        boolean hammer = inventory.contains(HAMMER);
        return hammer && oilyFishingRod && fishingBait;
    }

    private Instant lastTime = Instant.now();

    @Subscribe
    public void onGameTick(GameTick gameTick){
        if(overlayVisible != -1){
            checkOverlay();
        }
        ItemContainer equipment = client.getItemContainer(InventoryID.WORN);
        Item gloves = equipment != null ? equipment.getItem(EquipmentInventorySlot.GLOVES.getSlotIdx()) : null;
        boolean shouldAddOverlay = (gloves == null && checkInventory());

        if (shouldAddOverlay && config.anglerEquipped() && checkFullAngler() && checkItemsInInventory()){
            if(overlayVisible == -1){
                addOverlay();
            }
        } else if (shouldAddOverlay && !config.anglerEquipped() && checkItemsInInventory()){
            if (overlayVisible == -1){
                addOverlay();
            }
        } else   {
            if(overlayVisible != -1){
                removeOverlay();
            }
        }
    }

    @Override
	protected void startUp() throws Exception
	{
		overlayVisible = -1;
	}

	@Override
	protected void shutDown() throws Exception
	{
		if (overlayManager != null){
            overlayManager.remove(glovesOverlay);
        }
	}

    private void addOverlay(){
        if (overlayManager != null) {
            overlayManager.add(glovesOverlay);
            overlayVisible = client.getTickCount();
            lastTime = Instant.now();
        }
    }

    private void removeOverlay(){
        overlayManager.remove(glovesOverlay);
        overlayVisible = -1;
    }

    private void checkOverlay(){
        if (!checkGloves()){
            removeOverlay();
        }
    }

}
