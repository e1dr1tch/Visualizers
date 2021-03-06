#include "shader.hpp"

Shader::Shader(){}

GLuint Shader::loadShaders(string vertex_path, string fragment_path)
{
    const char *vertex_file_path = vertex_path.c_str();
    const char *fragment_file_path = fragment_path.c_str();

    // Create the shaders
	GLuint VertexShaderID = glCreateShader(GL_VERTEX_SHADER);
	GLuint FragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);

	// Read the Vertex Shader code from the file
	string VertexShaderCode;
	ifstream VertexShaderStream(vertex_file_path, ios::in);

	if(VertexShaderStream.is_open())
    {
		stringstream sstr;
		sstr << VertexShaderStream.rdbuf();
		VertexShaderCode = sstr.str();
		VertexShaderStream.close();
    }
    else
    {
        throw(runtime_error("Impossible to open" + vertex_path + "\n"));
    }

    // Read the Fragment Shader code from the file
    string FragmentShaderCode;
    ifstream FragmentShaderStream(fragment_file_path, ios::in);

    if(FragmentShaderStream.is_open())
    {
        stringstream sstr;
        sstr << FragmentShaderStream.rdbuf();
        FragmentShaderCode = sstr.str();
        FragmentShaderStream.close();
    }
    else
    {
        throw(runtime_error("Impossible to open" + fragment_path + "\n"));
    }

    GLint Result = GL_FALSE;
    int InfoLogLength;

    // Compile Vertex Shader
    //printf("Compiling shader : %s\n", vertex_file_path);

    char const * VertexSourcePointer = VertexShaderCode.c_str();
    glShaderSource(VertexShaderID, 1, &VertexSourcePointer , NULL);
    glCompileShader(VertexShaderID);

    // Check Vertex Shader
    glGetShaderiv(VertexShaderID, GL_COMPILE_STATUS, &Result);
    glGetShaderiv(VertexShaderID, GL_INFO_LOG_LENGTH, &InfoLogLength);

    if ( InfoLogLength > 0 )
    {
        vector<char> VertexShaderErrorMessage(InfoLogLength+1);
        glGetShaderInfoLog(VertexShaderID, InfoLogLength, NULL, &VertexShaderErrorMessage[0]);
        printf("%s\n", &VertexShaderErrorMessage[0]);
    }


    // Compile Fragment Shader
    //printf("Compiling shader : %s\n", fragment_file_path);

    char const * FragmentSourcePointer = FragmentShaderCode.c_str();
    glShaderSource(FragmentShaderID, 1, &FragmentSourcePointer , NULL);
    glCompileShader(FragmentShaderID);

    // Check Fragment Shader
    glGetShaderiv(FragmentShaderID, GL_COMPILE_STATUS, &Result);
    glGetShaderiv(FragmentShaderID, GL_INFO_LOG_LENGTH, &InfoLogLength);

    if ( InfoLogLength > 0 )
    {
        vector<char> FragmentShaderErrorMessage(InfoLogLength+1);
        glGetShaderInfoLog(FragmentShaderID, InfoLogLength, NULL, &FragmentShaderErrorMessage[0]);
        printf("%s\n", &FragmentShaderErrorMessage[0]);
    }


    // Link the program
    //printf("Linking program\n");
    GLuint ProgramID = glCreateProgram();

    glAttachShader(ProgramID, VertexShaderID);
    glAttachShader(ProgramID, FragmentShaderID);
    glLinkProgram(ProgramID);

    // Check the program
    glGetProgramiv(ProgramID, GL_LINK_STATUS, &Result);
    glGetProgramiv(ProgramID, GL_INFO_LOG_LENGTH, &InfoLogLength);

    if ( InfoLogLength > 0 )
    {
        vector<char> ProgramErrorMessage(InfoLogLength+1);
        glGetProgramInfoLog(ProgramID, InfoLogLength, NULL, &ProgramErrorMessage[0]);
        printf("%s\n", &ProgramErrorMessage[0]);
    }


    glDetachShader(ProgramID, VertexShaderID);
    glDetachShader(ProgramID, FragmentShaderID);

    glDeleteShader(VertexShaderID);
    glDeleteShader(FragmentShaderID);

    ID = ProgramID;
    return ProgramID;
}

void Shader::use() const
{
    glUseProgram(ID);
}
        
void Shader::setMat4(string key, mat4 value)
{
    glUniformMatrix4fv(glGetUniformLocation(getID(), key.c_str()), 1, GL_FALSE, value_ptr(value));
}

void Shader::setMat3(string key, mat3 value)
{
    glUniformMatrix3fv(glGetUniformLocation(getID(), key.c_str()), 1, GL_FALSE, value_ptr(value));
}

void Shader::setVec4(string key, vec4 value)
{
    glUniform4f(glGetUniformLocation(getID(), key.c_str()), value.x, value.y, value.z, value.w);
}

void Shader::setVec3(string key, vec3 value)
{
    glUniform3f(glGetUniformLocation(getID(), key.c_str()), value.x, value.y, value.z);
}

void Shader::setVec2(string key, vec2 value)
{
    glUniform2f(glGetUniformLocation(getID(), key.c_str()), value.x, value.y);
}

void Shader::setDouble(string key, double value)
{
    glUniform1d(glGetUniformLocation(getID(), key.c_str()), value);
}    

void Shader::setFloat(string key, float value)
{
    glUniform1f(glGetUniformLocation(getID(), key.c_str()), value);
    
}

void Shader::setInt(string key, int value)
{
    glUniform1i(glGetUniformLocation(getID(), key.c_str()), value);
}
        
GLuint Shader::getID() const
{
    return ID;
}

Shader::~Shader(){}
