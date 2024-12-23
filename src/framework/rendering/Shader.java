package framework.rendering;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import framework.Window;

public class Shader {
	private int programId;

	public void bind() {
		GL20.glUseProgram(programId);

		int windowSizeLocation = GL20.glGetUniformLocation(programId,
				"windowSize");
		GL20.glUniform2f(windowSizeLocation, Window.width, Window.height);
	}

	//not used by default
	private static String mainVS = "#version 120\n" + "attribute vec2 aPos;\n"
			+ "attribute vec2 aTexCoord;\n" + "attribute vec4 aColor;\n"
			+ "uniform vec2 windowSize;\n" + "varying vec2 fragCoord;\n"
			+ "varying vec2 TexCoord;\n" + "varying vec4 VertexColor;\n"
			+ "void main() {\n" + "    fragCoord = aPos * 2.0;\n"
			+ "    fragCoord.x -= windowSize.x;\n"
			+ "    fragCoord.y = windowSize.y - fragCoord.y;\n"
			+ "    gl_Position = vec4(fragCoord / windowSize, 0.0, 1.0);\n"
			+ "    TexCoord = aTexCoord;\n" + "    VertexColor = aColor;\n"
			+ "}\n";
	private static String mainFS = "#version 120\n"
			+ "uniform sampler2D texSampler;\n"
			+ "varying vec2 TexCoord;\n"
			+ "varying vec4 VertexColor;\n"
			+ "varying vec2 fragCoord;\n"
			+ "void main() {\n"
			+ "    if (TexCoord.x == 0.0 && TexCoord.y == 0.0) {\n"
			+ "        gl_FragColor = VertexColor;\n"
			+ "    } else {\n"
			+ "        gl_FragColor = texture2D(texSampler, TexCoord) * VertexColor;\n"
			+ "    }\n" + "}\n";

	private void sourceStr(String vertexSrc, String fragSrc) {
		int vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		GL20.glShaderSource(vertexShader, vertexSrc);
		GL20.glCompileShader(vertexShader);
		if (GL20.glGetShader(vertexShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			throw new RuntimeException("Error compiling vertex shader: "
					+ GL20.glGetShaderInfoLog(vertexShader, 256));
		}

		int fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		GL20.glShaderSource(fragmentShader, fragSrc);
		GL20.glCompileShader(fragmentShader);
		if (GL20.glGetShader(fragmentShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			throw new RuntimeException("Error compiling fragment shader: "
					+ GL20.glGetShaderInfoLog(fragmentShader, 256));
		}

		programId = GL20.glCreateProgram();
		GL20.glAttachShader(programId, vertexShader);
		GL20.glAttachShader(programId, fragmentShader);
		GL20.glLinkProgram(programId);
		if (GL20.glGetProgram(programId, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
			throw new RuntimeException("Error linking shader program: "
					+ GL20.glGetProgramInfoLog(programId, 256));
		}

		GL20.glValidateProgram(programId);
		if (GL20.glGetProgram(programId, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
			throw new RuntimeException("Error validating shader program: "
					+ GL20.glGetProgramInfoLog(programId, 256));
		}

		GL20.glDeleteShader(vertexShader);
		GL20.glDeleteShader(fragmentShader);
	}

	public Shader() {
		sourceStr(mainVS, mainFS);
	}

	public Shader(String vertexPath, String fragmentPath) {
		try {
			StringBuilder vertexSource = new StringBuilder();
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader("./res/"
						+ vertexPath));
				String line;
				while ((line = reader.readLine()) != null) {
					vertexSource.append(line).append("\n");
				}
			} finally {
				if (reader != null) {
					reader.close();
				}
			}

			StringBuilder fragmentSource = new StringBuilder();
			try {
				reader = new BufferedReader(new FileReader("./res/"
						+ fragmentPath));
				String line;
				while ((line = reader.readLine()) != null) {
					fragmentSource.append(line).append("\n");
				}
			} finally {
				if (reader != null) {
					reader.close();
				}
			}

			sourceStr(vertexSource.toString(), fragmentSource.toString());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
