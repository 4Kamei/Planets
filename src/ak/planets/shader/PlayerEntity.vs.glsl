#version 150 core
in vec4 in_Position;
in vec4 in_Colour;

out vec4 passColour;
void main(void){
    glPosition = in_Position;
    out_Colour =in_Colour;
}
