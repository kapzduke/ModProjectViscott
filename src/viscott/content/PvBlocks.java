package viscott.content;

import arc.audio.Sound;
import arc.func.Prov;
import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.entities.bullet.MassDriverBolt;
import mindustry.gen.Sounds;
import mindustry.logic.LStatements;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.distribution.DuctRouter;
import mindustry.world.blocks.distribution.Junction;
import mindustry.world.blocks.distribution.MassDriver;
import mindustry.world.blocks.distribution.StackConveyor;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.environment.StaticWall;
import mindustry.world.blocks.heat.HeatProducer;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.liquid.LiquidBridge;
import mindustry.world.blocks.liquid.LiquidJunction;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.logic.LogicBlock;
import mindustry.world.blocks.power.Battery;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.production.HeatCrafter;
import mindustry.world.blocks.production.Pump;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.draw.*;
import mindustry.world.meta.BuildVisibility;
import viscott.world.block.defense.PvWall;
import viscott.world.block.distribution.MassConveyor;
import viscott.world.block.drill.Grinder;
import viscott.world.block.drill.LiquidGrinder;
import viscott.world.block.environment.DepositWall;
import viscott.world.block.logic.PvLogicBlock;
import viscott.world.block.power.ConstGenerator;
import viscott.world.block.unit.BulkUnitFactory;

import static mindustry.type.ItemStack.with;

public class PvBlocks {
    public static Block
            /*Floor's*/
            densePlate1,densePlate2,densePlate3,densePlate4,
            densePlate5,densePlate6,densePlate7,densePlate8,
            damagedDensePlate,denseMetalWall,bariumWall,bariumPowder,

            /*Ore's*/
            erbiumOre,lithiumOre,zirconiumOre,platinumOre,

            /*Deposit's*/
            erbiumDepositLarge,lithiumDepositLarge,platinumDepositLarge,zirconiumDepositLarge,
                    erbiumDeposit,lithiumDeposit,platinumDeposit,zirconiumDeposit,

                    /*Buildings*/

                    /*Conveyors*/micromassConveyor,massJunction,massRouter,microTransportGate,
                            megaTransportGate,
                            megaMassConveyor,megaMassJunction,megaMassRouter,
                            nueromassConveyor,

                    /*Drills*/harvestDrill,tetraDrill,spectrumDrill,
                    /*Grinders*/harvestGrinder,behemothGrinder,oilGrinder,

                    /*Power*/opticalNode,auditoryNode,compressedBattery,
                    /*Power Production*/oilConcentrator,smallCarbonPanel,largeCarbonPanel,lithiumDegenerator,
                                        keroseneGenerator,
                    /*Production*/siliconMassForge,particalAccelerator, keroseneMixer, keroseneHeater, carbonWeaver,
                    /*Liquids*/concentratedJunction,concentratedRouter,concentratedConduit,concentratedContainer,concentratedTank,megaTransportValve,microTransportValve,
                            micropulsePump,
                    /*Pressure related*/ pressureSource,
                    /*Unit Creation*/nueroSpawnPad,eliteSpawnPad,
                    /*Core's*/coreHover,
                            /*Walls*/
                            zirconWall,zirconWallLarge,
                            siliconeWall,siliconeWallLarge,
                            platinumWall,platinumWallLarge,
                            erbiumWall,erbiumWallLarge,
                            carbonWall,carbonWallLarge,
                    /*Logic*/
                            piscoProcessor,
                            /*Testing*/
                                    sus
                            ;
            public static void load()
            {
                /*Floor's Start*/
                densePlate1 = new Floor("dense-panel1",0){{localizedName = "Dense Panel 1";}};
                densePlate2 = new Floor("dense-panel2",0){{localizedName = "Dense Panel 2";}};
                densePlate3 = new Floor("dense-panel3",0){{localizedName = "Dense Panel 3";}};
                densePlate4 = new Floor("dense-panel4",0){{localizedName = "Dense Panel 4";}};
                densePlate5 = new Floor("dense-panel5",0){{localizedName = "Dense Panel 5";}};
                densePlate6 = new Floor("dense-panel6",0){{localizedName = "Dense Panel 6";}};
                densePlate7 = new Floor("dense-panel7",0){{localizedName = "Dense Panel 7";}};
                densePlate8 = new Floor("dense-panel8",0){{localizedName = "Dense Panel 8";}};
                damagedDensePlate = new Floor("damaged-dense-plate",3);
                denseMetalWall = new StaticWall("dense-metal-wall"){{variants = 2; localizedName = "Dense Metal Wall";}};
                bariumWall = new StaticWall("barium-wall"){{variants = 2; localizedName = "Barium Wall";}};
                bariumPowder = new Floor("barium-powder",3)
                {{
                    localizedName = "Barium Powder";
                    itemDrop = PvItems.barium;
                    playerUnmineable = true;

                }};
                /*Floor's End*/
                /*Ore's Start*/
                erbiumOre = new OreBlock("erbium-ore",PvItems.erbium)
                {{
                    variants = 3;
                    localizedName = "Erbium Ore";
                }};
                lithiumOre = new OreBlock("lithium-ore",PvItems.lithium)
                {{
                    variants = 3;
                    localizedName = "Lithium Ore";
                }};
                platinumOre = new OreBlock("platinum-ore",PvItems.platinum)
                {{
                    variants = 3;
                    localizedName = "Platinum Ore";
                }};
                zirconiumOre = new OreBlock("zirconium-ore",PvItems.zirconium)
                {{
                    variants = 3;
                    localizedName = "Zirconium Ore";
                }};
                /*Ore's End*/
                /*Deposit's Start*/
                erbiumDeposit = new DepositWall("erbium-deposit") //Todo
                {{
                    variants = 0;
                    tier = 2;
                    clipSize = 256;
                    localizedName = "Erbium Deposit";
                    itemDrop = PvItems.erbium;
                }};
                lithiumDeposit = new DepositWall("lithium-deposit") //Todo
                {{
                    variants = 0;
                    clipSize = 256;
                    localizedName = "Lithium Deposit";
                    itemDrop = PvItems.lithium;
                }};
                platinumDeposit = new DepositWall("platinum-deposit") //Todo
                {{
                    variants = 0;
                    clipSize = 256;
                    localizedName = "Platinum Deposit";
                    itemDrop = PvItems.platinum;
                    tier = 2;
                    hardness = 0.06f;
                }};
                zirconiumDeposit = new DepositWall("zirconium-deposit") //Todo
                {{
                    variants = 0;
                    clipSize = 256;
                    localizedName = "Zirconium Deposit";
                    itemDrop = PvItems.zirconium;
                    hardness = 0.02f;
                }};
                /*Deposit's End*/
                /*Building start*/
                micromassConveyor = new MassConveyor("micromass-conveyor")
                {{
                    requirements(Category.distribution, with(PvItems.zirconium,1));
                    localizedName = "Micromass Conveyor";
                    health = 50;
                    itemCapacity = 5;
                    speed = 20f/(300);
                }};
                nueromassConveyor = new MassConveyor("nueromass-conveyor")
                {{
                    requirements(Category.distribution, with(PvItems.barium,2,PvItems.zirconium,1));
                    localizedName = "NueroMass Conveyor";
                    health = 110;
                    itemCapacity = 10;
                    speed = 40f/(300);
                }};
                megaMassConveyor = new MassConveyor("megamass-conveyor")
                {{
                    requirements(Category.distribution, with(PvItems.carbonFiber,1));
                    localizedName = "MegaMass Conveyor";
                    health = 150;
                    itemCapacity = 20;
                    speed = 80f/(300);
                }};
                massJunction = new Junction("mass-junction")
                {{
                    requirements(Category.distribution, with(PvItems.zirconium,10));
                    localizedName = "Mass Junction";
                    health = 90;
                    capacity = 20;
                    speed = 20;
                    ((MassConveyor)micromassConveyor).junction = this;
                    ((MassConveyor)nueromassConveyor).junction = this;
                }};
                massRouter = new DuctRouter("mass-router")
                {{
                    requirements(Category.distribution, with(PvItems.zirconium,10));
                    localizedName = "Mass Router";
                    health = 85;
                    speed = 0;
                }};
                megaMassJunction = new Junction("megamass-junction")
                {{
                    requirements(Category.distribution, with(PvItems.zirconium,10));
                    localizedName = "MegaMass Junction";
                    health = 155;
                    capacity = 40;
                    speed = 1;
                    ((MassConveyor)megaMassConveyor).junction = this;
                }};
                megaMassRouter = new DuctRouter("megamass-router")
                {{
                    requirements(Category.distribution, with(PvItems.zirconium,10));
                    localizedName = "MegaMass Router";
                    health = 140;
                    itemCapacity = 40;
                    speed = 0;
                }};
                microTransportGate = new MassDriver("micro-transport-gate"){{
                    requirements(Category.distribution, with(PvItems.zirconium, 20, PvItems.erbium, 30));
                    size = 1;
                    localizedName = "Micro transport gate";
                    health = 180;
                    itemCapacity = 10;
                    reload = 0f;
                    range = 10*8f;
                    rotateSpeed = 360;
                    bullet = new MassDriverBolt(){{
                        lightOpacity = 0;
                        shake = 0;
                        hasColor = false;
                        trailWidth = 0;
                        trailLength = 0;
                        drawDisabled = true;
                        speed = 100;
                    }};
                    consumePower(5/60f);
                    shootSound = Sounds.none;
                    shake = 0;
                }};
                megaTransportGate = new MassDriver("mega-transport-gate"){{
                    requirements(Category.distribution, with(PvItems.zirconium, 50, PvItems.nobelium, 25, PvItems.lithium, 10));
                    size = 3;
                    localizedName = "Mega transport gate";
                    health = 180;
                    itemCapacity = 20;
                    rotateSpeed = 360;
                    reload = 0f;
                    range = 120*8f;
                    consumePower(330/60f);
                    shootSound = Sounds.none;
                    shake = 0;
                    bullet = new MassDriverBolt(){{
                        lightOpacity = 0;
                        shake = 0;
                        hasColor = false;
                        trailWidth = 0;
                        trailLength = 0;
                        drawDisabled = true;
                        speed = 100;
                    }};
                }};
                megaTransportValve = new LiquidBridge("mega-transport-valve"){{
                    //todo adjust items
                    requirements(Category.liquid, with(PvItems.zirconium, 50, PvItems.nobelium, 25, PvItems.lithium, 10));
                    size = 3;
                    localizedName = "Mega liquid transport gate";
                    health = 180;
                    liquidCapacity = 200;
                    //todo power consume
                    range = 120;
                    consumePower(330/60f);
                }};
                microTransportValve = new LiquidBridge("micro-transport-valve"){{
                    //todo adjust items
                requirements(Category.liquid, with(PvItems.zirconium, 50, PvItems.erbium, 25, PvItems.lithium, 10));
                size = 1;
                localizedName = "Micro liquid transport gate";
                health = 180;
                liquidCapacity = 200;
                range = 10;
                //todo power consume
                consumePower(330/60f);
            }};

                fieldExtractor = new Grinder("field-extractor")
                {{
                    requirements(Category.production, with(PvItems.zirconium,35));
                    localizedName = "Field Extractor";
                    health = 200;
                    liquidCapacity = 20;
                    size = 2;
                    range = 2;
                    speedPerOre = 0.1f;
                    itemCapacity = 20;
                    updateEffect = Fx.smokeCloud;
                }};
                squareExtractor = new Grinder("square-extractor")
                {{
                    requirements(Category.production, with(PvItems.zirconium,35,PvItems.lithium,5,Items.silicon,25)); //Todo
                    localizedName = "Square Extractor";
                    consumeLiquid(Liquids.oil, 4f);
                    range = 3;
                    tier = 2;
                    size = 2;
                    health = 190;
                    blockedItem = PvItems.erbium;
                }};
                cubicExtractor = new Grinder("cubic-extactor")
                {{
                    requirements(Category.production, with(PvItems.zirconium,100,PvItems.platinum,100)); //Todo
                    localizedName = "Cubic Extractor";
                    health = 570;
                    liquidCapacity = 20;
                    consumePower(50f/60f);
                    size = 3;
                    tier = 2;
                    range = 4;
                    speedPerOre = 0.2f;
                    itemCapacity = 40;
                    updateEffect = Fx.smeltsmoke;
                }};
                triDrill = new Drill("tri-drill")
                {{
                    requirements(Category.production, with(PvItems.zirconium,15));
                    localizedName = "Tri Drill";
                    size = 2;
                    drillTime = 500;
                    tier = 1;
                    liquidBoostIntensity = 2.5f;
                    health = 225;
                    liquidCapacity = 5;
                    consumeLiquid(Liquids.water,2.5f/60f).boost();
                }};
                tetraDrill = new Drill("tetra-drill")
                {{
                    requirements(Category.production, with(PvItems.zirconium,60,PvItems.platinum,25)); //Todo
                    localizedName = "Tetra Drill";
                    size = 3;
                    drillTime = 200;
                    tier = 2;
                    liquidBoostIntensity = 2.5f;
                    health = 400;
                    liquidCapacity = 20;
                    consumePower(70f/60f);
                    consumeLiquid(PvLiquids.kerosene,2.5f/60f).boost();
                }};
                spectrumDrill = new Drill("spectrum-drill")
                {{
                    requirements(Category.production, with(Items.silicon,125,PvItems.zirconium,150,PvItems.platinum,100,PvItems.erbium,75)); //Todo
                    localizedName = "Spectrum Drill";
                    size = 4;
                    drillTime = 160;
                    tier = 3;
                    liquidBoostIntensity = 1f;
                    health = 790;
                    liquidCapacity = 5;
                    consumePower(195f/60f);
                }};
                opticalNode = new PowerNode("optical-node")
                {{
                    requirements(Category.power, with(PvItems.zirconium,10,PvItems.lithium,5));
                    localizedName = "Optical node";
                    size = 1;
                    maxNodes = 4;
                    laserRange = 18;
                    health = 80;
                }};
                auditoryNode = new PowerNode("auditory-node")
                {{
                    requirements(Category.power, with(PvItems.zirconium,40,Items.silicon,10,PvItems.platinum,15));
                    localizedName = "Auditory node";
                    size = 2;
                    maxNodes = 6;
                    laserRange = 35;
                    health = 220;
                }};
                compressedBattery = new Battery("compressed-battery")
                {{
                    requirements(Category.power, with(PvItems.lithium,50,PvItems.platinum,25,Items.silicon,25));
                    localizedName = "Compressed Battery";
                    consumePowerBuffered(75000);
                    size = 2;
                    health = 410;
                    emptyLightColor = Color.black;
                    fullLightColor = Color.valueOf("8fcaf2");
                }};
                oilConcentrator = new ThermalGenerator("oil-concentrator")
                {{
                    requirements(Category.power, with(PvItems.zirconium,20,PvItems.lithium,30));
                    localizedName = "Oil Concentrator";
                    health = 80;
                    attribute = Attribute.oil;
                    powerProduction = 1f / 3f;
                    floating = true;
                    ambientSound = Sounds.hum;
                    ambientSoundVolume = 0.015f;
                }};       
                smallCarbonPanel = new ConstGenerator("small-carbon-panel")
                {{
                    requirements(Category.power, with(Items.silicon,15,PvItems.carbonFiber,5));
                    localizedName = "Carbon panel";
                    size = 2;
                    powerProduction = 24f/60f;
                    health = 275;
                }};
                largeCarbonPanel = new ConstGenerator("large-carbon-panel")
                {{
                    requirements(Category.power, with(Items.silicon,65,PvItems.carbonFiber,30));
                    localizedName = "Large Carbon panel";
                    size = 4;
                    powerProduction = 130f/60f;
                    health = 780;
                }};
                lithiumDegenerator = new ConsumeGenerator("lithium-degenerator")
                {{
                    requirements(Category.power, with(PvItems.zirconium,110,PvItems.barium,65,PvItems.nobelium,25)); //Todo
                    localizedName = "Lithium Degenerator";
                    size = 2;
                    powerProduction = 120/60f;
                    health = 250;
                    consumeLiquid(Liquids.water,4f/60f);
                    consumeItem(PvItems.lithium);
                    liquidCapacity = 10;
                    itemCapacity = 5;
                    itemDuration = 1.8f*60f;
                    outputLiquid = new LiquidStack(Liquids.nitrogen,5f/60f);
                    generateEffect = Fx.generate;
                }};
                keroseneGenerator = new ConsumeGenerator("kerosene-generator")
                {{
                    requirements(Category.power, with(PvItems.zirconium,140,PvItems.platinum,25,PvItems.lithium,100)); //Todo
                    localizedName = "Kerosene Generator";
                    health = 270;
                    size = 2;
                    consumeLiquid(PvLiquids.kerosene,8f/60f);
                    liquidCapacity = 30;
                    powerProduction = 430f/60f;
                    itemDuration = 60f;
                    generateEffect = Fx.smokeCloud;
                    drawer = new DrawMulti(
                        new DrawDefault(),
                        new DrawWarmupRegion(),
                            new DrawRegion("-rotator")
                            {{
                                rotateSpeed = 2;
                                spinSprite = true;

                            }},
                            new DrawRegion("-top")
                    );
                }};
                siliconMassForge = new GenericCrafter("silicon-mass-forge")
                {{
                    requirements(Category.crafting, with(PvItems.zirconium,35,PvItems.lithium,20));
                    localizedName = "Silicon Mass Forge";
                    health = 175;
                    size = 2;
                    consumeItems(with(PvItems.barium,3));
                    consumeLiquid(Liquids.oil,15f/60f);
                    consumePower(45f/60f);
                    itemCapacity = 20;
                    craftTime = 3.3f*60f;
                    outputItem = new ItemStack(Items.silicon,5);
                }};
                particleAccelerator = new HeatCrafter("particle-accelerator")
                {{
                    requirements(Category.crafting, with(PvItems.zirconium,50,PvItems.platinum,30,Items.silicon,50));
                    localizedName = "Particle Accelerator";
                    health = 230;
                    size = 2;
                    consumeItems(with(PvItems.zirconium,5));
                    consumePower(60f/60f);
                    itemCapacity = 10;
                    craftTime = 5.8f*60f;
                    heatRequirement = 6;
                    maxEfficiency = 2;
                    outputItem = new ItemStack(PvItems.nobelium,3);
                }};
                keroseneMixer = new GenericCrafter("kerosene-mixer")
                {
                    {
                        requirements(Category.crafting, with(PvItems.zirconium, 50, PvItems.platinum, 30, PvItems.lithium, 30));
                        localizedName = "Kerosene Mixer";
                        health = 185;
                        size = 2;
                        consumeItems(with(PvItems.zirconium, 2));
                        consumeLiquid(Liquids.oil, 20 / (60 * 2f));
                        consumePower(20f / 60f);
                        itemCapacity = 10;
                        liquidCapacity = 10;
                        craftTime = 2f * 60f;
                        outputLiquid = new LiquidStack(PvLiquids.kerosene, 15 / (60 * 2f));
                    }};
                    carbonWeaver = new GenericCrafter("carbon-weaver")
                    {{
                        requirements(Category.crafting, with(PvItems.zirconium,50,PvItems.platinum,30,Items.silicon,50));
                        localizedName = "Carbon Weaver";
                        health = 1300;
                        size = 3;
                        consumeItems(with(PvItems.zirconium,7, Items.silicon, 5, PvItems.platinum, 5));
                        consumeLiquid(PvLiquids.kerosene, 25/(60*6.7f));
                        consumePower(280f/60f);
                        itemCapacity = 10;
                        liquidCapacity = 10;
                        craftTime = 6.7f*60f;
                        outputItem = new ItemStack(PvItems.carbonFiber, 3);
                }};
                keroseneHeater = new HeatProducer("kerosene-heater"){{
                    requirements(Category.crafting, with(PvItems.zirconium, 20, PvItems.platinum, 30));

                    drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput());
                    rotateDraw = false;
                    size = 2;
                    localizedName = "Kerosene Heater";
                    heatOutput = 4f;
                    regionRotated1 = 1;
                    ambientSound = Sounds.hum;
                    liquidCapacity = 10;
                    craftTime = 5.8f*60;
                    consumeLiquid(PvLiquids.kerosene, 5/(60*5.8f));
                }};
                concentratedRouter = new LiquidRouter("concentrated-router")
                {{
                    requirements(Category.liquid, with(PvItems.lithium,4,PvItems.zirconium,4));
                    localizedName = "Concentrated Router";
                    health = 60;
                    liquidCapacity = 40;
                }};
                concentratedContainer = new LiquidRouter("concentrated-container")
                {{
                    requirements(Category.liquid, with(PvItems.lithium,25,PvItems.platinum,20));
                    size = 2;
                    localizedName = "Concentrated Container";
                    scaledHealth = 60;
                    liquidCapacity = 800;
                }};
                concentratedTank = new LiquidRouter("concentrated-router")
                {{
                    requirements(Category.liquid, with(PvItems.lithium,80,PvItems.platinum,60));
                    size = 3;
                    localizedName = "Concentrated Tank";
                    scaledHealth = 60;
                    liquidCapacity = 2000;
                }};
                concentratedJunction = new LiquidJunction("concentrated-junction")
                {{
                    requirements(Category.liquid, with(PvItems.lithium,4,PvItems.zirconium,4));
                    localizedName = "Concentrated Junction";
                    health = 60;
                    liquidCapacity = 40;
                }};
                concentratedConduit = new Conduit("concentrated-conduit")
                {{
                    requirements(Category.liquid, with(PvItems.lithium,4,PvItems.zirconium,2));
                    localizedName = "Concentrated conduit";
                    health = 60;
                    liquidCapacity = 30;
                    junctionReplacement = concentratedJunction;
                }};
                neuroSpawnPad = new BulkUnitFactory("neuro-spawn-pad")
                {{
                    requirements(Category.units,with(Items.silicon,100,PvItems.zirconium,250,PvItems.platinum,50)); //Todo
                    localizedName = "Neuro Spawn Pad";
                    health = 1600;
                    size = 5;
                    squareSprite = false;
                    consumePower(130f/60f);
                    itemCapacity = 3000;
                    liquidCapacity = 200;
                    plans = new Seq<>().with(
                        new UnitPlan(PvUnits.particle,25*60f,with(Items.silicon,30,PvItems.lithium,15)),
                    new UnitPlan(PvUnits.snippet,35*60f,with(Items.silicon,30,PvItems.lithium,20,PvItems.nobelium,10))
                    );
                }};
                eliteSpawnPad = new BulkUnitFactory("elite-spawn-pad")
                {{
                    requirements(Category.units,with(Items.silicon,800,PvItems.zirconium,650,PvItems.platinum,250,PvItems.nobelium,50)); //Todo
                    localizedName = "Elite Spawn Pad";
                    health = 3400;
                    size = 7;
                    squareSprite = false;
                    consumePower(480f/60f);
                    itemCapacity = 8000;
                    liquidCapacity = 500;
                    maxAmount = 20;
                    plans = new Seq<>().with(
                            new UnitPlan(PvUnits.particle,25*60f,with(Items.silicon,30,PvItems.lithium,15)),
                                    new UnitPlan(PvUnits.snippet,35*60f,with(Items.silicon,30,PvItems.lithium,20,PvItems.nobelium,10))
                    );
                }};
                coreHover = new CoreBlock("core-hover")
                {{
                    requirements(Category.effect, with(PvItems.zirconium,1000,PvItems.lithium,1500,Items.silicon,300));
                    localizedName = "Core Hover";
                    alwaysUnlocked = true;
                    unitType = PvUnits.micro;
                    health = 1350;
                    size = 3;
                    unitCapModifier = 30;
                    itemCapacity = 6000;
                }};
                zirconWall = new Wall("zircon-wall")
                {{
                    requirements(Category.defense, with(PvItems.zirconium,4));
                    localizedName = "Zircon Wall";
                    health = 400;
                }};
                zirconWallLarge = new Wall("zircon-wall-large")
                {{
                    requirements(Category.defense, with(PvItems.zirconium,24));
                    localizedName = "Large Zircon Wall";
                    health = 1680;
                    size = 2;
                }};
                siliconeWall = new Battery("silicone-wall")
                {{
                    requirements(Category.defense, with(Items.silicon,4));
                    localizedName = "Silicone Wall";
                    health = 260;
                    consumePowerBuffered(500);
                }};
                siliconeWallLarge = new Battery("silicone-wall-large")
                {{
                    requirements(Category.defense, with(Items.silicon,24));
                    localizedName = "Large Silicone Wall";
                    health = 1175;
                    size = 2;
                    consumePowerBuffered(2000);
                }};
                platinumWall = new PvWall("platinum-wall")
                {{
                    requirements(Category.defense, with(PvItems.platinum,4));
                    localizedName = "Platinum Wall";
                    health = 575;
                    pierceReduction = 1;
                }};
                platinumWallLarge = new PvWall("platinum-wall-large")
                {{
                    requirements(Category.defense, with(PvItems.platinum,24));
                    localizedName = "Large Platinum Wall";
                    health = 2240;
                    pierceReduction = 2;
                    size = 2;
                }};
                erbiumWall = new PvWall("erbium-wall")
                {{
                    requirements(Category.defense, with(PvItems.erbium,4));
                    localizedName = "Erbium Wall";
                    health = 930;
                    absorbLasers = true;
                }};
                erbiumWallLarge = new PvWall("erbium-wall-large")
                {{
                    requirements(Category.defense, with(PvItems.erbium,24));
                    localizedName = "Large Erbium Wall";
                    health = 3800;
                    size = 2;
                    absorbLasers = true;
                }};
                carbonWall = new PvWall("carbon-wall")
                {{
                    requirements(Category.defense, with(PvItems.carbonFiber,6));
                    localizedName = "Carbon Wall";
                    health = 1120;
                    pierceReduction = 1;
                }};
                carbonWallLarge = new PvWall("carbon-wall-large")
                {{
                    requirements(Category.defense, with(PvItems.carbonFiber,30));
                    localizedName = "Large Carbon Wall";
                    health = 5250;
                    size = 2;
                    pierceReduction = 2;
                }};
                micropulsePump = new Pump("micropulse-pump")
                {{
                    requirements(Category.liquid, with(PvItems.zirconium,40,PvItems.lithium,60));
                    localizedName = "MicroPulse Pump";
                    size = 2;
                    pumpAmount = 0.1f;
                    liquidCapacity = 40;
                }};
                piscoProcessor = new PvLogicBlock("pisco-processor")
                {{
                    requirements(Category.logic, with(PvItems.zirconium,75,PvItems.lithium,50,PvItems.nobelium,20));
                    localizedName = "Pisco Processor";
                    range = 8*6;
                    health = 90;
                    instructionsPerTick = 1;
                    maxInstructionsPerTick = 1;
                    allStatements= Seq.with(
                            new Prov[]{
                                    LStatements.UnitBindStatement::new,
                                    PvLogic.CommentStatement::new,
                                    PvLogic.HurtStatement::new
                            });
                }};
                sus = new PvWall("sus")
                {{
                    requirements(Category.defense, BuildVisibility.sandboxOnly, with(PvItems.nobelium,1));
                    localizedName = "Sus";
                    description = "its litteraly 1 peice of nobelium wi-\n[red]Emergency Meeting[]\n[orange]Orange :[] [red]Red[] is Sus!\n[red]Red :[] What.\n[grey]Red was the Imposter[]";
                    health = 8000;
                    armor = 10;
                    pierceReduction = 99;
                    absorbLasers = true;
                }};
            }
}
