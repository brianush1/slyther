/**
 * 
 */
package com.buckosoft.glelements;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.*;
//import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.*;
import org.lwjgl.system.*;

import static java.lang.Math.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryStack.*;
import static net.gegy1000.slyther.util.IOUtil.*;

/**
 * @author dick
 *
 */
public class TrueTypeFont {
	private ByteBuffer ttf;

	private STBTTFontinfo info;

	@SuppressWarnings("unused")
	private int ascent;
	private int descent;
	@SuppressWarnings("unused")
	private int lineGap;
	private int fontHeight;
	private float contentScaleX = 1.0f;
	private float contentScaleY = 1.0f;
	private STBTTBakedChar.Buffer cdata = null;
	private int BITMAP_W;
	private int BITMAP_H;
	private int texID;
	private boolean kerningEnabled = true;
    private boolean lineBBEnabled = false;

	public void load(String font, int size) {
		fontHeight = size;
		try {
			ttf = ioResourceToByteBuffer("FreeSans.ttf", 512 * 1024);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		info = STBTTFontinfo.create();
		if (!stbtt_InitFont(info, ttf)) {
			throw new IllegalStateException("Failed to initialize font information.");
		}

		try (MemoryStack stack = stackPush()) {
			IntBuffer pAscent  = stack.mallocInt(1);
			IntBuffer pDescent = stack.mallocInt(1);
			IntBuffer pLineGap = stack.mallocInt(1);

			stbtt_GetFontVMetrics(info, pAscent, pDescent, pLineGap);

			ascent = pAscent.get(0);
			descent = pDescent.get(0);
			lineGap = pLineGap.get(0);

//			if (Platform.get() == Platform.MACOSX) {
//				framebufferW = ww;
//				framebufferH = wh;
//			} else {
//				framebufferW = round(ww * contentScaleX);
//				framebufferH = round(wh * contentScaleY);
//			}
		}
		try (MemoryStack s = stackPush()) {
			FloatBuffer px = s.mallocFloat(1);
			FloatBuffer py = s.mallocFloat(1);

			long monitor = glfwGetPrimaryMonitor();
			glfwGetMonitorContentScale(monitor, px, py);

			contentScaleX = px.get(0);
			contentScaleY = py.get(0);
		}
		resizeFont();
	}

	public float getWidth(String text) {
		int width = 0;
		int from = 0;
		int to = text.length()-1;

		try (MemoryStack stack = stackPush()) {
			IntBuffer pCodePoint       = stack.mallocInt(1);
			IntBuffer pAdvancedWidth   = stack.mallocInt(1);
			IntBuffer pLeftSideBearing = stack.mallocInt(1);

			int i = from;
			while (i <= to) {
				i += getCP(text, to, i, pCodePoint);
				int cp = pCodePoint.get(0);

				stbtt_GetCodepointHMetrics(info, cp, pAdvancedWidth, pLeftSideBearing);
				width += pAdvancedWidth.get(0);

				if (kerningEnabled && i < to) {
					getCP(text, to, i, pCodePoint);
					width += stbtt_GetCodepointKernAdvance(info, cp, pCodePoint.get(0));
				}
			}
		}

		return width * stbtt_ScaleForPixelHeight(info, fontHeight);
	}

	public float getHeight() {
		return(fontHeight);
	}

//	public void drawString(float f, float g, String text, Color color) {
		
//	}
	public void drawString(float f, float g, String text, int color) {
//		f /= 640;
//		g /= 480;
		float scale = stbtt_ScaleForPixelHeight(info, fontHeight);

		try (MemoryStack stack = stackPush()) {
			IntBuffer pCodePoint = stack.mallocInt(1);

			FloatBuffer x = stack.floats(0.0f);
			FloatBuffer y = stack.floats(0.0f);

			STBTTAlignedQuad q = STBTTAlignedQuad.mallocStack(stack);

			int lineStart = 0;

			float factorX = 1.0f / contentScaleX;
			float factorY = 1.0f / contentScaleY;

			float lineY = 0.0f;
			lineY = g + fontHeight;
			x.put(0, f);
			y.put(0, lineY);

	        glEnable(GL_TEXTURE_2D);
	        glBindTexture(GL_TEXTURE_2D, texID);
			glBegin(GL_QUADS);
			int alpha = color >> 24 & 0xFF;
			if (alpha == 0)
				alpha = 0xFF;
			glColor4f((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, alpha / 255.0F);
			for (int i = 0, to = text.length(); i < to; ) {
				i += getCP(text, to, i, pCodePoint);

				int cp = pCodePoint.get(0);
				if (cp == '\n') {
					if (lineBBEnabled) {
						glEnd();
						renderLineBB(text, lineStart, i - 1, f, y.get(0), scale);
						glBegin(GL_QUADS);
					}

					//y.put(0, lineY = y.get(0) + (ascent - descent + lineGap) * scale);
					y.put(0, lineY);
					x.put(0, f);

					lineStart = i;
					continue;
				} else if (cp < 32 || 128 <= cp) {
					continue;
				}

				float cpX = x.get(0);
				stbtt_GetBakedQuad(cdata, BITMAP_W, BITMAP_H, cp - 32, x, y, q, true);
				x.put(0, scale(cpX, x.get(0), factorX));
				if (kerningEnabled && i < to) {
					getCP(text, to, i, pCodePoint);
					x.put(0, x.get(0) + stbtt_GetCodepointKernAdvance(info, cp, pCodePoint.get(0)) * scale);
				}

				float
				x0 = scale(cpX, q.x0(), factorX),
				x1 = scale(cpX, q.x1(), factorX),
				y0 = scale(lineY, q.y0(), factorY),
				y1 = scale(lineY, q.y1(), factorY);

				glTexCoord2f(q.s0(), q.t0());
				glVertex2f(x0, y0);

				glTexCoord2f(q.s1(), q.t0());
				glVertex2f(x1, y0);

				glTexCoord2f(q.s1(), q.t1());
				glVertex2f(x1, y1);

				glTexCoord2f(q.s0(), q.t1());
				glVertex2f(x0, y1);
			}
			glEnd();
			if (lineBBEnabled) {
				renderLineBB(text, lineStart, text.length(), f, lineY, scale);
			}
		}
		
	}

	public void resizeFont() {
        BITMAP_W = round(512 * contentScaleX);
        BITMAP_H = round(512 * contentScaleY);
        if (cdata != null)
        	cdata.free();
        cdata = init(BITMAP_W, BITMAP_H);	// XXX leak existing cdata?

	}
	
    private STBTTBakedChar.Buffer init(int BITMAP_W, int BITMAP_H) {
        texID = glGenTextures();
        STBTTBakedChar.Buffer cdata = STBTTBakedChar.malloc(96);

        ByteBuffer bitmap = BufferUtils.createByteBuffer(BITMAP_W * BITMAP_H);
        stbtt_BakeFontBitmap(ttf, fontHeight * contentScaleY, bitmap, BITMAP_W, BITMAP_H, 32, cdata);

        glBindTexture(GL_TEXTURE_2D, texID);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_ALPHA, BITMAP_W, BITMAP_H, 0, GL_ALPHA, GL_UNSIGNED_BYTE, bitmap);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

        glClearColor(43f / 255f, 43f / 255f, 43f / 255f, 0f); // BG color
        glColor3f(169f / 255f, 183f / 255f, 198f / 255f); // Text color

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        return cdata;
    }

    private static int getCP(String text, int to, int i, IntBuffer cpOut) {
        char c1 = text.charAt(i);
        if (Character.isHighSurrogate(c1) && i + 1 < to) {
            char c2 = text.charAt(i + 1);
            if (Character.isLowSurrogate(c2)) {
                cpOut.put(0, Character.toCodePoint(c1, c2));
                return 2;
            }
        }
        cpOut.put(0, c1);
        return 1;
    }

    private static float scale(float center, float offset, float factor) {
        return (offset - center) * factor + center;
    }

    private void renderLineBB(String text, int from, int to, float x, float y, float scale) {
        glDisable(GL_TEXTURE_2D);
        glPolygonMode(GL_FRONT, GL_LINE);
        glColor3f(1.0f, 1.0f, 0.0f);

        float width = getWidth(text);
        y -= descent * scale;

        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x+width, y);
        glVertex2f(x+width, y - fontHeight);
        glVertex2f(x, y - fontHeight);
        glEnd();

        glEnable(GL_TEXTURE_2D);
        glPolygonMode(GL_FRONT, GL_FILL);
        glColor3f(169f / 255f, 183f / 255f, 198f / 255f); // Text color
    }

}
