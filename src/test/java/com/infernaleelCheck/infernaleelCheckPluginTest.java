package com.infernaleelCheck;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class infernaleelCheckPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(infernaleelCheckPlugin.class);
		RuneLite.main(args);
	}
}