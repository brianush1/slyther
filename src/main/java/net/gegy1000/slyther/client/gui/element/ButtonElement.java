package net.gegy1000.slyther.client.gui.element;

import net.gegy1000.slyther.client.gui.Gui;
import org.lwjgl.opengl.GL11;

import java.util.function.Function;

public class ButtonElement extends Element {
    private String text;
    private String texture0 = null;
    private String texture1 = null;
    private Function<ButtonElement, Boolean> function;

    /** Construct a button with text
     * @param gui
     * @param text
     * @param posX
     * @param posY
     * @param width
     * @param height
     * @param function
     */
    public ButtonElement(Gui gui, String text, float posX, float posY, float width, float height, Function<ButtonElement, Boolean> function) {
        super(gui, posX - (width / 2.0F), posY - (height / 2.0F), width, height);
        this.text = text;
        this.function = function;
    }

    /** Construct a button that uses a specific texture.
     * Used by GuiCustomSkin
     * @param gui
     * @param posX
     * @param posY
     * @param width
     * @param height
     * @param texture0
     * @param texture1
     * @param function
     */
    public ButtonElement(Gui gui, float posX, float posY, float width, float height,
    		String texture0, String texture1, Function<ButtonElement, Boolean> function) {
        super(gui, posX - (width / 2.0F), posY - (height / 2.0F), width, height);
        this.function = function;
        this.texture0 = texture0;
        this.texture1 = texture1;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(float mouseX, float mouseY) {
        boolean selected = isSelected(mouseX, mouseY);
        if (texture0 != null) {
        	String t = selected ? texture1 : texture0;
        	drawButton(posX, posY, width, height, t);
        } else {
	        int color = selected ? 0x3A7E5C : 0x489E73;
	        GL11.glColor4f((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, 1.0F);
	        drawButton(posX, posY, width, height);
	        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	        gui.drawCenteredString(text, posX + (width / 2.0F), posY + (height / 2.0F), 0.5F, 0xFFFFFF);
        }
    }

    @Override
    public boolean mouseClicked(float mouseX, float mouseY, int button) {
        boolean selected = button == 0 && super.isSelected(mouseX, mouseY);
        if (selected) {
            return function.apply(this);
        }
        return false;
    }

	@Override
	public void keyPressed(int key) {
	}

	@Override
	public void charPressed(int character) {
	}
}
