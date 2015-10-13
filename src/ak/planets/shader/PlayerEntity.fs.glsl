#version 150 core

in vec4 passColour;

out vec4 out_Colour;

void main(void){
    out_Colour = passColour;
}