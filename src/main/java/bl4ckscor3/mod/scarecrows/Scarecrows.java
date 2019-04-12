package bl4ckscor3.mod.scarecrows;

import java.util.Arrays;

import bl4ckscor3.mod.scarecrows.block.BlockArm;
import bl4ckscor3.mod.scarecrows.block.BlockInvisibleLight;
import bl4ckscor3.mod.scarecrows.entity.EntityScarecrow;
import bl4ckscor3.mod.scarecrows.proxy.ServerProxy;
import bl4ckscor3.mod.scarecrows.util.CustomDataSerializers;
import net.minecraft.block.Block;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@Mod(modid=Scarecrows.MODID, name=Scarecrows.NAME, version=Scarecrows.VERSION, acceptedMinecraftVersions=Scarecrows.MC_VERSION)
@EventBusSubscriber
public class Scarecrows
{
	public static final String MODID = "scarecrows";
	public static final String NAME = "Scarecrows";
	public static final String VERSION = "v1.0.4";
	public static final String MC_VERSION = "1.12";
	public static final String PREFIX = MODID + ":";
	@SidedProxy(clientSide="bl4ckscor3.mod.scarecrows.proxy.ClientProxy", serverSide="bl4ckscor3.mod.scarecrows.proxy.ServerProxy")
	public static ServerProxy proxy;

	@ObjectHolder(Scarecrows.PREFIX + BlockArm.NAME)
	public static final Block ARM = null;
	@ObjectHolder(Scarecrows.PREFIX + BlockInvisibleLight.NAME)
	public static final Block INVISIBLE_LIGHT = null;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ModMetadata modMeta = event.getModMetadata();

		modMeta.authorList = Arrays.asList(new String[] {
				"bl4ckscor3"
		});
		modMeta.autogenerated = false;
		modMeta.description = "Adds scarecrows that keep monsters (and animals if you want) off your lawn.";
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.registerRenderers();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		DataSerializers.registerSerializer(CustomDataSerializers.AXISALIGNEDBB);
		DataSerializers.registerSerializer(CustomDataSerializers.SCARECROWTYPE);
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(new BlockArm());
		event.getRegistry().register(new BlockInvisibleLight());
	}

	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityEntry> event)
	{
		event.getRegistry().register(EntityEntryBuilder.create()
				.id(new ResourceLocation(MODID, "scarecrow"), 0)
				.entity(EntityScarecrow.class)
				.name(PREFIX + "scarecrow")
				.tracker(256, 20, false).build());
	}

	@SubscribeEvent
	public static void onConfigChanged(OnConfigChangedEvent event)
	{
		if(event.getModID().equals(MODID))
			ConfigManager.sync(MODID, Type.INSTANCE);
	}
}
