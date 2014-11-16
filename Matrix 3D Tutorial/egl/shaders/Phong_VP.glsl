#version 150 
precision highp float;

/***********************************************************************
MARC System - Affective interaction
Authors: Matthieu Courgeon

Copyright (C) 2010 LIMSI-CNRS (France)
This file is provided without support, instruction, or implied
warranty of any kind.  Authors make no guarantee of its
fitness for a particular purpose and are not liables under any
circumstances for any damages or loss whatsoever arising from the use
or inability to use this file or items derived from it.
*************************************************************************/

out vec3 Vue                ;
out vec3 Lumiere1           ;
out vec3 Lumiere2           ;
out vec2 decalCoords        ;
out vec4 ligthViewCoord ;

uniform mat4 ModelView;
uniform mat4 ModelViewProj;
uniform mat4 WorldViewInv;
uniform mat4 lightViewProj;

in vec4 vx_Position;
in vec3 vx_Normal;
in vec2 vx_TexCoord;

uniform vec4 L0_Position;
uniform vec4 L1_Position;

void main() {

	gl_Position = ModelViewProj * vx_Position;
   
	Vue = -(ModelView * vx_Position).xyz;
	vec3 Normal = (ModelView * vec4(vx_Normal,0.0)).xyz;
   
	Lumiere1.xyz = (L0_Position).xyz + Vue;   
	Lumiere2.xyz = (L1_Position).xyz + Vue;   
   
	decalCoords = vx_TexCoord;
   
   ligthViewCoord = lightViewProj*(WorldViewInv*(ModelView*vx_Position));
   
   
   
	vec3 tangent = cross(normalize(vec3(0.01,1.0,0.01)),normalize(Normal.xyz));
   
	vec3 n = (ModelView*vec4(normalize( Normal ),0)).xyz;
	vec3 t = (ModelView*vec4(normalize( tangent),0)).xyz;
	vec3 b = cross(n,t);
	
	mat3 ObservateurAEspTangent = mat3(	t.x,b.x,n.x,
										t.y,b.y,n.y,
										t.z,b.z,n.z) ;
										
	Lumiere1.xyz = ((ObservateurAEspTangent* Lumiere1.xyz));	
	Lumiere2.xyz = ((ObservateurAEspTangent* Lumiere2.xyz));
	Vue     = ((ObservateurAEspTangent* Vue.xyz));	
   
}
