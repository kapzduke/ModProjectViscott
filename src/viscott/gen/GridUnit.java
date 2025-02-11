package viscott.gen;

import arc.func.Cons;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.Scaled;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Tmp;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.core.World;
import mindustry.entities.abilities.Ability;
import mindustry.entities.part.DrawPart;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.gen.MechUnit;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.type.Item;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.Tiles;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.distribution.StackConveyor;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.draw.DrawTurret;
import viscott.content.PvBlocks;
import viscott.types.GridUnitType;

import java.util.concurrent.atomic.AtomicBoolean;

import static mindustry.Vars.*;
import static mindustry.Vars.itemSize;

public class GridUnit extends MechUnit {
    public World innerWorld = new World();
    public boolean built = false;
    public int buildSize = 0;
    public boolean[][] buildArea = new boolean[0][0];
    GridUnitType gu;
    public GridUnit(GridUnitType type) {
        this();
        gu = type;
        build();
    }

    public GridUnit() {
        super();
    }

    void build() {
        buildSize = gu.buildSize;
        buildArea = gu.buildArea;
        innerWorld = buildGrid();
    }

    World buildGrid() {
        if (ui.editor == null) return null;
        World gridWorld = new World();
        gridWorld.resize(buildSize,buildSize);
        Cons<Tiles> gen = (tiles) -> {
            for(int iy = 0;iy < buildSize;iy++)
                for(int ix = 0;ix < buildSize;ix++) {
                    tiles.set(ix,iy,new Tile(ix,iy, PvBlocks.densePlate, Blocks.air,Blocks.air));
                }
        };
        gen.get(gridWorld.tiles);
        return gridWorld;
    }

    public boolean buildAt(Tile c,int x, int y, Building building, byte rotation) {
        int cx = c.x,
                cy = c.y;
        if (building == null || building.block() == null) return false;
        if (building.block instanceof CoreBlock) return false;
        World curWorld = Vars.world;
        int s = buildSize-1;
        Tile cT = building.tile;
        Tile t = innerWorld.tile(0,0);
        int size = building.block().size;
        int min = -Mathf.floor((size - 1) / 2),
                max = Mathf.floor(size / 2);
        int offset = (building.block().size-1) % 2;
        switch(rotation%4) {
            case 0:
                t = innerWorld.tile(cT.x - cx,cT.y - cy);
                if (!buildArea[y][x]) return false;
                break;
            case 1:
                t = innerWorld.tile(cT.y - cy,s- (cT.x - cx)-offset);
                if (!buildArea[x][y]) return false;
                break;
            case 2:
                t = innerWorld.tile(s- (cT.x - cx)-offset,s- (cT.y - cy)-offset);
                if (!buildArea[s-y][s-x]) return false;
                break;
            case 3:
                t = innerWorld.tile(s- (cT.y - cy)-offset,cT.x - cx);
                if (!buildArea[s-x][s-y]) return false;
                break;
        }
        if (t == null) return false;
        if (t.x + min < 0 || t.y + min < 0) return false;
        if (t.x + max >= buildSize || t.y + max >= buildSize) return false;
        if (building.tile.build == building)
            building.tile.setNet(Blocks.air);
        Vars.world = innerWorld;
        building.rotation -= rotation;
        building.rotation %= 4;
        t.setBlock(building.block(),team,building.rotation,()->building);
        if (Groups.all.contains(b->b==building)) {
            building.setIndex__all(-1);
            Groups.all.remove(building);
        }

        Vars.world = curWorld;
        return true;
    }

    public boolean placeFrom(Tile c,int x,int y,byte rotation) {
        int bx = c.x,
                by = c.y;
        Building b = innerWorld.tile(x,y).build;
        if (b == null || b.block() == null) return false;

        int size = b.block().size;
        int min = -Mathf.floor((size - 1) / 2),
                max = Mathf.floor(size / 2);

        Tile cT = b.tile();
        for(int i1 = min;i1 <= max;i1++)
            for(int i2 = min;i2 <= max;i2++)
                innerWorld.tile(cT.x+i1,cT.y+i2).setBlock(Blocks.air);
        int s = buildSize-1;
        Tile t = Vars.world.tile(0,0);
        int offset = (b.block().size-1) % 2;
        switch(rotation%4) {
            case 0:
                t = Vars.world.tile(bx + cT.x,by + cT.y);
                break;
            case 1:
                t = Vars.world.tile(bx + s - cT.y-offset,by + cT.x);
                break;
            case 2:
                t = Vars.world.tile(bx + s - cT.x-offset,by + s - cT.y-offset);
                break;
            case 3:
                t = Vars.world.tile(bx + cT.y,by + s - cT.x-offset);
                break;
        }
        if (t == null) {
            Log.warn("No Tile found at : " + (bx + cT.x) + ", " + (by + cT.y) + " | bx : " + bx + ", by : " + by, 0);
            return false;
        }
        b.rotation += rotation;
        b.rotation %= 4;
        t.setBlock(b.block(),team,b.rotation,()->b);
        b.set(t.x * 8 + (b.block().size-1) % 2 * 4,t.y * 8 + (b.block().size-1) % 2 * 4);
        if(!Groups.all.contains(r->r == b)) {
            int i = Groups.all.addIndex(b);
            b.setIndex__all(i);
        }
        return true;
    }

    @Override
    public void update() {
        super.update();
        if (!isFlying()) {
            x = Mathf.ceil(x / 8) * 8 - 4;
            y = Mathf.ceil(y / 8) * 8 - 4;
            rotation = Math.round(rotation / 90) * 90;
            int bx = Mathf.ceil(x / 8) - buildSize / 2,
                    by = Mathf.ceil(y / 8) - buildSize / 2;
            Tile bt = Vars.world.tile(bx,by);
            if (built) { //unbuild time
                Seq<Building> proxupdate = new Seq<>();
                for (int i1 = 0; i1 < buildSize; i1++) {
                    for (int i2 = 0; i2 < buildSize; i2++) {
                        Tile t = innerWorld.tile(i1, i2);
                        if (t.build != null && t.block() != null)
                            proxupdate.add(t.build);
                        placeFrom(bt,i1, i2,  (byte) Math.round(rotation / 90));
                    }
                }
                proxupdate.each(b -> {
                    if (!b.isAdded())
                        b.add();
                    b.updateProximity();
                });
                built = false;
            }
        } else if (!built) {//building time
            int bx = Mathf.ceil(x / 8) - buildSize / 2,
                    by = Mathf.ceil(y / 8) - buildSize / 2;
            Tile rt = Vars.world.tile(bx,by);
            Seq<Building> proxupdate = new Seq<>();
            for (int i1 = 0; i1 < buildSize; i1++) {
                for (int i2 = 0; i2 < buildSize; i2++) {
                    Tile t = rt.nearby(i1,i2);
                    if (t != null && t.block() != null) {
                        if (t.build != null)
                            proxupdate.add(t.build);
                        buildAt(rt,i1, i2,  t.build, (byte) Math.round(rotation / 90));
                    }
                }
            }
            World w = Vars.world;
            Vars.world = innerWorld;
            proxupdate.each(b -> {
                if (!b.isAdded())
                    b.add();
            });
            proxupdate.each(b -> {
                b.onProximityUpdate();
            });
            Vars.world = w;
            built = true;
        }

        Seq<Building> updated = new Seq<>();
        World w = Vars.world;
        Tiles tiles = innerWorld.tiles;
        Vars.world = innerWorld;
        innerWorld.tiles.each((x,y) -> {
            Tile tile = tiles.get(x,y);
            if (tile.block() != null && !updated.contains(tile.build)) {
                if (tile.build != null && tile.build.block() != null)
                    tile.build.update();
                    updated.add(tile.build);
            }
        });
        Vars.world = w;
    }
    @Override
    public void draw() {
        float z = isFlying() ? Layer.flyingUnitLow : Layer.block-1;
        if(controller().isBeingControlled(player.unit())){
            type.drawControl(this);
        }
        if((isFlying() || type.shadowElevation > 0)){
            Draw.z(Math.min(Layer.darkness, z - 1f));
            type.drawShadow(this);
        }
        Draw.z(z-0.1f);
        if(type.drawBody)
            type.drawOutline(this);
        type.drawWeaponOutlines(this);
        if(type.engineLayer > 0)
            Draw.z(type.engineLayer);
        if(type.trailLength > 0 && !type.naval && (isFlying() || !type.useEngineElevation)){
            type.drawTrail(this);
        }
        if(type.engines.size > 0)
            type.drawEngines(this);
        Draw.z(z);
        if(type.drawBody)
            type.drawBody(this);
        if(type.drawCell)
            type.drawCell(this);
        type.drawWeapons(this);
        if(type.drawItems)
            type.drawItems(this);
        type.drawLight(this);

        if(shieldAlpha > 0 && type.drawShields){
            type.drawShield(this);
        }

        if(type.parts.size > 0){
            for(int i = 0; i < type.parts.size; i++){
                var part = type.parts.get(i);

                WeaponMount first = mounts.length > part.weaponIndex ? mounts[part.weaponIndex] : null;
                if(first != null){
                    DrawPart.params.set(first.warmup, first.reload / type.weapons.first().reload, first.smoothReload, first.heat, first.recoil, first.charge, x, y, rotation);
                }else{
                    DrawPart.params.set(0f, 0f, 0f, 0f, 0f, 0f, x, y, rotation);
                }

                if(this instanceof Scaled s){
                    DrawPart.params.life = s.fin();
                }

                part.draw(DrawPart.params);
            }
        }

        for(Ability a : abilities){
            Draw.reset();
            a.draw(this);
        }

        Draw.reset();

        Seq<Building> drawed = new Seq<>();
        World w = Vars.world;
        Tiles t = innerWorld.tiles;
        Vars.world = innerWorld;
        innerWorld.tiles.each((x,y) -> {
            float xOffset = (x - buildSize / 2) * 8 + 4;
            float yOffset = (y -  buildSize / 2) * 8 + 4;

            Building build = t.get(x,y).build;
            if (build != null && !drawed.contains(build)) {
                if (build.block() == null) return;
                Draw.z(z + 1f);
                int size = build.block().size;
                float off = Mathf.floor((size - 1) * 4);
                float Dx = this.x + Angles.trnsx(rotation, xOffset + off, yOffset + off);
                float Dy = this.y + Angles.trnsy(rotation, xOffset + off, yOffset + off);
                build.x = Dx;
                build.y = Dy;
                build.payloadRotation = rotation;
                if (build instanceof Turret.TurretBuild tb) {
                    Turret turret = (Turret) tb.block;
                    DrawTurret drawer = (DrawTurret) turret.drawer;
                    Draw.rect(drawer.base, Dx, Dy, rotation);
                    Draw.color();
                    Drawf.shadow(drawer.preview, Dx + tb.recoilOffset.x - turret.elevation, Dy + tb.recoilOffset.y - turret.elevation, tb.drawrot());
                    Draw.z(z + 1.1f);
                    drawer.drawTurret(turret, tb);
                    drawer.drawHeat(turret, tb);
                    drawTurretParts(tb, Dx, Dy);
                }
                else if (build instanceof Conveyor.ConveyorBuild cb) {
                    float rot = rotation+build.rotation*90;
                    Draw.rect(build.block().getGeneratedIcons()[0], Dx, Dy, rot);
                    for(int i = 0; i < cb.len; i++){
                        Item item = cb.ids[i];
                        Tmp.v1.trns(rot, tilesize, 0);
                        Tmp.v2.trns(rot, -tilesize / 2f, cb.xs[i] * tilesize / 2f);

                        float
                                ix = (cb.x + Tmp.v1.x * cb.ys[i] + Tmp.v2.x),
                                iy = (cb.y + Tmp.v1.y * cb.ys[i] + Tmp.v2.y);

                        //keep draw position deterministic.
                        Draw.z(Draw.z()+0.1f);
                        Draw.rect(item.fullIcon, ix, iy, itemSize, itemSize);
                    }
                }
                else if (build instanceof StackConveyor.StackConveyorBuild sb) {
                    Draw.rect(build.block().region,Dx,Dy,rotation + build.rotation*90);
                    StackConveyor bl = (StackConveyor) build.block();
                    Tile from = world.tile(sb.link);
                    if (from == null) return;
                    int fromRot = from.build == null ? sb.rotation : from.build.rotation;
                    float a = (fromRot%4) * 90;
                    float b = (rotation%4) * 90;
                    if((fromRot%4) == 3 && (rotation%4) == 0) a = -1 * 90;
                    if((fromRot%4) == 0 && (rotation%4) == 3) a =  4 * 90;
                    Draw.rect(bl.stackRegion, Dx, Dy, Mathf.lerp(a, b, rotation+Interp.smooth.apply(1f - Mathf.clamp(sb.cooldown * 2, 0f, 1f))));
                } else {
                    if (!build.block().rotate)
                        build.rotation = 0;
                    Draw.rect(build.block().region, Dx, Dy, rotation+build.rotation*90);
                    if (build.block().teamRegion.found())
                        Draw.rect(build.block().teamRegions[team.id],Dx,Dy,rotation+build.rotation*90);
                }

                drawed.add(build);
                Draw.reset();
            }
        });
        Vars.world = w;
    }

    void drawTurretParts(Turret.TurretBuild tb, float x, float y) {
        DrawTurret drawer = (DrawTurret) ((Turret) tb.block()).drawer;
        if(drawer.parts.size > 0){
            Draw.z(Layer.flyingUnitLow+1.18f);
            if(drawer.outline.found()){
                //draw outline under everything when parts are involved
                Draw.z(Layer.flyingUnitLow+1.15f);
                Draw.rect(drawer.outline, x + tb.recoilOffset.x, y + tb.recoilOffset.y, tb.drawrot());
            }

            float progress = tb.progress();

            //TODO no smooth reload
            var params = DrawPart.params.set(tb.warmup(), 1f - progress, 1f - progress, tb.heat, tb.curRecoil, tb.charge, x + tb.recoilOffset.x, y + tb.recoilOffset.y, tb.rotation);

            for(var part : drawer.parts){
                params.setRecoil(part.recoilIndex >= 0 && tb.curRecoils != null ? tb.curRecoils[part.recoilIndex] : tb.curRecoil);
                part.draw(params);
            }
        }
    }

    @Override
    public int classId() {
        return 151;
    }

    @Override
    public void read(Reads read) {
        super.read(read);
        built = read.bool();
        int typeId = read.i();
        if (content.units().find(u->u.id == typeId) instanceof GridUnitType g) {
            gu = g;
            build();
        }

        World w = world;
        world = innerWorld;
        if (built) {
            innerWorld.tiles.each((x,y) -> {
                Tile t = innerWorld.tile(x,y);
                int bId = read.i();
                if (bId != -1) {
                    int rot = read.s();
                    Block b = content.block(bId);
                    t.setBlock(b,team,rot);
                    t.build.read(read);
                }
            });
        }
        world = w;
    }

    @Override
    public void write(Writes write) {
        super.write(write);
        write.bool(built);
        write.i(gu.id);
        if (built) {
            innerWorld.tiles.each((x,y) -> {
                Tile t = innerWorld.tile(x,y);
                if (t.build == null || t == t.build.tile)
                    write.i(-1);
                else {
                    write.i(t.build.block().id);
                    write.s(t.build.rotation);
                    t.build.write(write);
                }

            });
        }
    }
}
