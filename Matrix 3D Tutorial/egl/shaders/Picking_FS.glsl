#version 150
precision highp float;

/***********************************************************************
MARC System - Affective interaction
Authors: Matthieu Courgeon & Christian Jacquemin

Copyright (C) 2010 LIMSI-CNRS (France)
This file is provided without support, instruction, or implied
warranty of any kind.  Authors make no guarantee of its
fitness for a particular purpose and are not liables under any
circumstances for any damages or loss whatsoever arising from the use
or inability to use this file or items derived from it.
*************************************************************************/

uniform int ID;

uniform int TexCoordMode;

in vec2 px_TexCoord;

out vec4 color;

void main()
{
	
	if (TexCoordMode==0){
		int id = ID; 
		float b = float(mod(id,256))/255.0;
		id /= 256; 
		float g = float(mod(id,256))/255.0;
		id /= 256; 
		float r = float(mod(id,256))/255.0;
		id /= 256;
	
		color = vec4(r,g,b,1.0);
	}else{
		color = vec4(px_TexCoord,ID,1.0);		
	}  
			
}
