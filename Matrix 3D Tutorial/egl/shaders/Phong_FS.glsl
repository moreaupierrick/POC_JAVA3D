#version 150 
precision highp float;

/***********************************************************************
Authors: Matthieu Courgeon 

Copyright (C) 2010 Matthieu COURGEON
This file is provided without support, instruction, or implied
warranty of any kind.  Authors make no guarantee of its
fitness for a particular purpose and are not liables under any
circumstances for any damages or loss whatsoever arising from the use
or inability to use this file or items derived from it.
*************************************************************************/

uniform sampler2D ColorTex  ;
uniform sampler2D NormalMap  ;

uniform vec4 obj_Diffuse  ;
uniform vec4 obj_Back_Diffuse;
uniform vec4 obj_Ambient  ;
uniform vec4 obj_Specular  ;
uniform vec4 obj_Emission  ;
uniform float obj_Shininess  ;

uniform float CameraExposure;

uniform vec3 L0_Color;
uniform vec3 L1_Color;


in vec3 Vue                ;
in vec3 Lumiere1           ;
in vec3 Lumiere2           ;
in vec2 decalCoords        ;

out vec4 out_Color		   ;
out vec4 out_Shadow		   ;


float saturate(float inp){
	return (inp>1.0?1.0:(inp<0.0?0.0:inp));	
}

vec3 saturate(vec3 inp){
	return vec3(
		saturate(inp.x),
		saturate(inp.y),
		saturate(inp.z)
	);	
}

void main()
{
	
		vec3 Normal = texture(NormalMap, decalCoords).xyz*2.0 - 1.0;
	
 vec4 L0_Diffuse  ;

		vec3 V = normalize(Vue);
		vec3 N = normalize(Normal);
		vec3 L1 = normalize(Lumiere1);
		vec3 L2 = normalize(Lumiere2);
		vec3 H1 = normalize(V+L1);
		vec3 H2 = normalize(V+L2);
		
		vec4 texColor = texture(ColorTex, decalCoords);
		
		if ((texColor.a==0.0) && (texColor.r==0.0) && (texColor.g==0.0) && (texColor.b==0.0))
			texColor=vec4(1,1,1,1);
		
		
		vec3 spec1 = pow(saturate(dot(H1,N)),obj_Shininess)*obj_Specular.rgb*L0_Color.rgb;
		vec3 spec2 = pow(saturate(dot(H2,N)),obj_Shininess)*obj_Specular.rgb*L1_Color.rgb;
		
		vec3 diffuse1 = saturate(dot(L1,N))*obj_Diffuse.rgb*L0_Color.rgb + saturate(dot(-L1,N))*obj_Back_Diffuse.rgb*L0_Color.rgb;
		vec3 diffuse2 = saturate(dot(L2,N))*obj_Diffuse.rgb*L1_Color.rgb + saturate(dot(-L2,N))*obj_Back_Diffuse.rgb*L1_Color.rgb;
		
		vec3 ambient1 = obj_Ambient.rgb;
		vec3 ambient2 = obj_Ambient.rgb;
		
		vec3 lumCoef =  (diffuse1+diffuse2 
						+ambient1+ambient2 
						+obj_Emission.rgb);
		
		lumCoef.r = saturate(lumCoef.r);
		lumCoef.g = saturate(lumCoef.g);
		lumCoef.b = saturate(lumCoef.b);
		
		out_Color = vec4(texColor.rgb*lumCoef + saturate(spec1 + spec2	), texColor.a*obj_Diffuse.a);
		
		out_Color.rgb *= CameraExposure;
		
		//out_Color.rgb *= 0.0001;
		//out_Color.rgb += L1.xyz*0.5 + 0.5;
		
			
}
