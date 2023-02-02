package viscott.world.block.drill;


import arc.Core;
import arc.math.Mathf;
import arc.util.Strings;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.Tile;
import viscott.world.block.PvBlock;
import viscott.world.block.environment.DepositWall;

import static mindustry.Vars.*;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Stream;

public class Grinder extends PvBlock {

    public int range = 1;
    public float speedPerOre = 0.2f;
    public float maxProgress = 100;
    public Grinder(String name)
    {
        super(name);
        update = true;
        hasItems = true;
        itemCapacity = 10;
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        return true;
    }


    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x,y,rotation,valid);
        int fix = (size % 2) * 4;
        Drawf.dashRect(Pal.lighterOrange,x*8-offset-range*8-fix,y*8-offset-range*8-fix,size * 8 + range * 16,size * 8 + range * 16);
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("grindspeed", (GrinderBuild e) ->
                new Bar(() -> Core.bundle.format("bar.grindspeed", Strings.fixed(e.maxMineSpeed * 60 / 100 * e.timeScale(), 2)), () -> Pal.lighterOrange, () -> e.progress));
    }

    public class GrinderBuild extends Building
    {
        public List<Block> mineable;
        float maxMineSpeed;

        @Override
        public void created()
        {
            super.created();
            mineable = visibleBlocks();
            List<Block> newMineable = new Stack<>();
            for(Block m : mineable)
                if (m instanceof DepositWall)
                    newMineable.add(m);
            mineable = newMineable;
            maxMineSpeed = mineable.size() * speedPerOre;
        }

        public List<Block> visibleBlocks()
        {
            int ix = ((int)x/8)-(int)Math.floor((size-1)/2)-range,
                    iy = ((int)y/8)-(int)Math.floor((size-1)/2)-range,
                    rangeSize = size + range * 2;
            List<Block> newBlockList = new Stack<>();
            for (int i1 = 0;i1<rangeSize;i1++)
                for (int i2 = 0;i2<rangeSize;i2++)
                    newBlockList.add(world.tiles.get(ix+i1,iy+i2).block());
            return newBlockList;
        }

        float progress;
        float mine = 0;
        @Override
        public void update()
        {
            progress = Mathf.approachDelta(progress,1,maxMineSpeed/100);
            mine = Mathf.approachDelta(mine,0,timeScale()*delta()/20);
            if (progress == 1) {
                mineable.forEach(a ->
                {
                    if (items.get(a.itemDrop) < itemCapacity)
                        items.add(a.itemDrop, 1);
                });
                progress = 0;
                mine = 1;
            }
            dump();
        }

        @Override
        public void draw()
        {
            super.draw();
            Drawf.dashRect(Pal.lighterOrange,x-mine*4,y-mine*4,mine*8,mine*8);
        }
    }
}
