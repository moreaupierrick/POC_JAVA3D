#version 150 
precision highp float;

out vec3 Vue                ;

uniform mat4 ModelViewProj;
uniform mat4 ModelView;

in vec4 vx_Position;



void main() {


   gl_Position = ModelViewProj * vx_Position;
   
   Vue = -(ModelView * vx_Position).xyz;   
      
}
