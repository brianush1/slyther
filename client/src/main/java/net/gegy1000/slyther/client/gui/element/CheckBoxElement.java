/**
 * 
 */
package net.gegy1000.slyther.client.gui.element;

import java.util.function.Function;

import net.gegy1000.slyther.client.gui.Gui;

/**
 * @author dick
 *
 */
public class CheckBoxElement extends Element {
	private boolean state;
	private	String	text;
    private Function<CheckBoxElement, Boolean> function;

    /** Construct a checkbox
     * @param gui
     * @param state whether this checkbox is checked or not
     * @param text The string to display with the checkbox
     * @param posX
     * @param posY
     * @param width
     * @param height
     * @param function
     */
	public CheckBoxElement(Gui gui, boolean state, String text, float posX, float posY, float width, float height, Function<CheckBoxElement, Boolean> function) {
        super(gui, posX - (width / 2.0F), posY - (height / 2.0F), width, height);
        this.state = state;
        this.text = text;
        this.function = function;

	}

	@Override
	public void render(float mouseX, float mouseY) {
        boolean selected = isSelected(mouseX, mouseY);
        int color = selected ? buttonSelectedColor : buttonColor;
        gui.drawBox(posX, posY, width, height, color);
        gui.drawString(text, posX+width+5, posY, 0.6F, color);
        if (state) {
        	color = selected ? altButtonSelectedColor : altButtonColor;
        	gui.drawLine(posX, posY, posX+width, posY+height, 2, color);
        	gui.drawLine(posX, posY+height, posX+width, posY, 2, color);
        }
	}

	@Override
	public boolean mouseClicked(float mouseX, float mouseY, int button) {
	    boolean selected = button == 0 && super.isSelected(mouseX, mouseY);
        if (selected) {
        	state = !state;
            return function.apply(this);
        }
        return false;
    	}
	/**
	 * @return the state
	 */
	public boolean isChecked() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setChecked(boolean state) {
		this.state = state;
	}
	
	@Override
	public void keyPressed(int key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void charPressed(int character) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	
}
