package net.gegy1000.slyther.client.gui.element;

import org.lwjgl.opengl.GL11;
import static org.lwjgl.glfw.GLFW.*;

import java.util.function.Function;
import net.gegy1000.slyther.client.gui.Gui;
import net.gegy1000.slyther.util.Log;

public class TextBoxElement extends Element {
	private String text;
	private Function<TextBoxElement, Void> function;
	private boolean selected;
	private int tick;
	private int selectionIndex;

	public TextBoxElement(Gui gui, String text, float posX, float posY, float width, float height, Function<TextBoxElement, Void> function) {
		super(gui, posX - (width / 2.0F), posY - (height / 2.0F), width, height);
		this.text = text;
		this.function = function;
		selectionIndex = text.length();
	}

	@Override
	public void update() {
		tick++;
	}

	@Override
	public void render(float mouseX, float mouseY) {
		int color = selected ? 0x684782 : 0x8D60AF;
		GL11.glColor4f((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, 1.0F);
		drawButton(posX, posY, width, height);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		gui.drawCenteredString(text, posX + (width / 2.0F), posY + (height / 2.0F), 0.5F, 0xFFFFFF);
		if (selected && tick % 40 > 20) {
			float x = (gui.font.getWidth(text.substring(0, selectionIndex)) / 2.0F) - gui.font.getWidth(text) / 4.0F;
			gui.drawRect(posX + (width / 2.0F) + x, posY + (height / 2.0F) - (gui.font.getHeight() / 2.0F), 1.0F, gui.font.getHeight());
		}
	}

	@Override
	public boolean mouseClicked(float mouseX, float mouseY, int button) {
		selected = button == 0 && super.isSelected(mouseX, mouseY);
		Log.debug("textBoxElement selected {}", selected);
		selectionIndex = text.length();
		return false;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		String previous = this.text;
		this.text = text;
		if (!previous.equals(text)) {
			function.apply(this);
		}
	}

	@Override
	public void keyPressed(int key) {
		if (selected) {
			boolean modified = false;
			if (key == GLFW_KEY_BACKSPACE) {
				if (text.length() > 0 && selectionIndex > 0) {
					text = text.substring(0, Math.max(0, selectionIndex - 1)) + text.substring(selectionIndex);
					selectionIndex--;
					modified = true;
				}
			} else if (key == GLFW_KEY_LEFT && selectionIndex > 0) {
				selectionIndex--;
			} else if (key == GLFW_KEY_RIGHT && selectionIndex < text.length()) {
				selectionIndex++;
			} else if (key == GLFW_KEY_HOME)
				selectionIndex = 0;
			else if (key == GLFW_KEY_END)
				selectionIndex = text.length();
			if (modified) {
				function.apply(this);
			}
		}
	}

	@Override
	public void charPressed(int character) {
		if (selected) {
			char char1 = (char)character;
			text = text.substring(0, selectionIndex) + char1 + text.substring(selectionIndex);
			selectionIndex++;
			function.apply(this);
		}
	}
}
