#version 150 
precision highp float;


in vec3 Vue                ;

out vec4 out_Color		   ;

void main()
{
	out_Color.rgba = vec4(Vue.z,0,0,1);
	//out_Color.rgba = vec4(1,0,0,1);			
		
}
