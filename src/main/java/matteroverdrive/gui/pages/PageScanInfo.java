package matteroverdrive.gui.pages;

import cofh.lib.gui.GuiColor;
import cofh.lib.gui.element.ElementButton;
import matteroverdrive.Reference;
import matteroverdrive.gui.MOGuiBase;
import matteroverdrive.util.MatterDatabaseHelper;
import matteroverdrive.util.MatterHelper;
import matteroverdrive.util.RenderUtils;
import matteroverdrive.gui.element.*;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * Created by Simeon on 4/3/2015.
 */
public class PageScanInfo extends ElementBaseGroup
{
    public static final String backgroundPath = Reference.PATH_GUI + "matter_scanner.png";
    public static final String LIST_ELEMENT_NAME = "list";
    public static final String SCROLL_UP_BUTTON_NAME = "scroll_up";
    public static final String SCROLL_DOWN_BUTTON_NAME = "scroll_down";

    NBTTagCompound itemNBT;

    public MatterDatabaseListBox list;
    ElementProgress scan_progress;
    ElementScanProgress scan_info_graph;
    MOElementTextField searchField;
    ElementButton scrollButtonUp;
    ElementButton scrollButtonDown;

    ElementItemPreview itemPreview;

    public PageScanInfo(MOGuiBase gui, int posX, int posY, String name,NBTTagCompound itemNBT,ItemStack scanner)
    {
        super(gui, posX, posY);
        this.setName(name);
        this.itemNBT = itemNBT;

        scan_info_graph = new ElementScanProgress(gui,94,44);
        itemPreview = new ElementItemPreview(gui,45,44,null);
        scan_progress = new ElementProgress(gui,44 + 35,202 + 2,44,202,39,202,62,188,105,14,142,18);
        searchField = new MOElementTextField(gui,41,26,242,14);
        list = new MatterDatabaseListBox(gui,3,39,42,152,scanner);
        scrollButtonUp = new ElementButton(gui,11,27,SCROLL_UP_BUTTON_NAME,22,188,32,188,10,10,backgroundPath);
        scrollButtonDown = new ElementButton(gui,11,190,SCROLL_DOWN_BUTTON_NAME,42,188,52,188,10,10,backgroundPath);

        list.setName(LIST_ELEMENT_NAME);

        list.setFilter("");

        scan_progress.setTexture(backgroundPath, 256, 256);
        scan_progress.setMaxValue(MatterDatabaseHelper.MAX_ITEM_PROGRESS);
        scan_progress.SetTextPostition(18, 5);
        scan_progress.setTextColor(new GuiColor(255, 255, 255).getColor());
        scan_info_graph.setProgress(1);
    }

    @Override
    public void init()
    {
        super.init();

        elements.add(scan_info_graph);
        elements.add(itemPreview);
        elements.add(scan_progress);
        elements.add(searchField);
        elements.add(scrollButtonUp);
        elements.add(scrollButtonDown);
        elements.add(list);
    }

    @Override
    public void drawForeground(int mouseX, int mouseY)
    {
        super.drawForeground(mouseX, mouseY);



        ItemStack item = MatterDatabaseHelper.GetItemStackFromNBT(itemNBT);

        if(item != null)
        {
            //GL11.glEnable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glAlphaFunc(GL11.GL_GREATER,0.5f);
            List infos = item.getTooltip(null, false);

            if (MatterHelper.CanScan(item))
            {
                String Matter = "Matter: " + String.valueOf(MatterHelper.getMatterAmountFromItem(item)) + MatterHelper.MATTER_UNIT;
                infos.add(Matter);
            }

            RenderUtils.DrawMultilineInfo(infos, 50, 98, 8, 200, new GuiColor(255, 255, 255).getColor());
        }
        else
        {
            String Matter = "Matter: " + String.valueOf(MatterHelper.getMatterAmountFromItem(item)) + MatterHelper.MATTER_UNIT;
            Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("No Item Selected!",80,90,new GuiColor(255,150,50).getColor());
        }
    }

    @Override
    public void update(int mouseX, int mouseY)
    {
        super.update(mouseX, mouseY);

        if(searchField != null)
        {
            this.list.setFilter(searchField.getText());
        }
    }

    public void updateList(NBTTagList list)
    {
        this.list.updateList(list);
    }

    public void setItemNBT(NBTTagCompound tagCompound)
    {
        itemNBT = tagCompound;

        scan_progress.setVisible(itemNBT != null && MatterHelper.CanScan(MatterDatabaseHelper.GetItemStackFromNBT(tagCompound)));
        scan_info_graph.setVisible(itemNBT != null);
        itemPreview.setVisible(itemNBT != null);

        if (tagCompound != null)
        {
            scan_progress.setValue(MatterDatabaseHelper.GetProgressFromNBT(itemNBT));
            scan_progress.setText(String.valueOf((int) (((float) MatterDatabaseHelper.GetProgressFromNBT(itemNBT) / (float) 100) * 100)) + "%");


            scan_info_graph.setSeed(tagCompound.getShort("id"));
            itemPreview.setItemStack(ItemStack.loadItemStackFromNBT(tagCompound));
        }
    }
}