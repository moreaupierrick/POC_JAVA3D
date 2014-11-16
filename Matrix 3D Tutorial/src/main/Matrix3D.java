package main;

import java.util.LinkedList;

public class Matrix3D {

	/*
	/*===============================================================================================================================
	/*===============================================================================================================================
	/*===============================================================================================================================
	/*===============================================================================================================================
	 * 
	 * CONVENTION IMPORTANTE : LES MATRICES SONT STOQUEE DANS DES TABLEAUX DE FLOAT A UNE DIMENTION ET DE LONGEUR 16
	 * 
	 * LES INDEX A UNE DIMENTION SONT ORGANISES COMME SUIT : 
	 * 
	 *     0	1	2	3
	 *     4	5	6	7
	 *     8	9	10	11
	 *     12	13	14	15
	 *     
	 *     
	/*===============================================================================================================================
	/*===============================================================================================================================
	/*===============================================================================================================================
	/*===============================================================================================================================
	 */

	/*===============================================================================================================================
	 * FONCTIONS PRIVEES UTILES 
	 *===============================================================================================================================
	 */
	
	
	/* Questions 3.1 */ 
	private static float [] tr = new float[16];
	private static float [] sc = new float[16];
	private static float [] RX = new float[16];
	private static float [] RY = new float[16];
	private static float [] RZ = new float[16];
	private static float [] result = new float[16];
	
	/* Questions 3.2 */ 
	private static float [][] pop = new float[21][16];
	static int indCourant =0;
	
	/**
	 * COPIE LA MATRICE src DANS LA MATRICE dst
	 * 
	 * @param src
	 * @param dst
	 */
	private static void copy(float[] src, float [] dst){
		int taille = src.length;
		//Sécurité
		if(taille != dst.length){
			System.err.println("ERREUR : Les deux matrices n'ont pas la même taille");
			return;
		}
		
		for(int i = 0; i < taille; i++)
			dst[i] = src[i];
	}
	
	/**
	 * Multipli la matrice 4x4 M1 par la matrice 4x4 M2, puis rend le resultat dans MR
	 * ATTENTION, l'appel a la methode doit reussir meme si M1 et MR pointent sur le meme tableau !!!
	 * 
	 * @param m1
	 * @param m2
	 * @param mR
	 * 
	 */
	private static void multiply_matrix(float[] m1, float [] m2, float [] mR){
		float tmp = 0;
		
		//Pour chaque ligne de m1
		for(int i = 0; i < 16;i=i+4){
			
			//Pour chaque colonne de m2
			for(int j = 0; j < 4; j++){
				
				//Pour chaque coeff de la ligne de m1
				for(int k = 0; k < 4; k++){
					tmp += m1[k+i]*m2[4*k+j];
				}
				result[i+j] = tmp;
				tmp = 0;
			}
		}
		copy(result,mR);
	}

	/*===============================================================================================================================
	 * FONCTIONS GEOMETRIQUES 
	 *===============================================================================================================================
	 */
	
	
	/**
	 * Remplace le contenu de la matrice par la matrice identite.
	 * 
	 * @param matrix
	 */
	public static void setIdentity(float[] matrix){
		for(int i = 0; i < 16; i++){
			if(i%5 == 0) matrix[i] = 1;
			else matrix[i] = 0;
		}
				
	}
	
	/**
	 * EFFECTUE LA MULTIPLICATION MATRICIELLE DE matrix POUR EFFECTUER UNE TRANSLATION DE (x,y,z)
	 * @param matrix
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void translate(float[] matrix, float x, float y, float z){

		setIdentity(tr);
		tr[12] = x; tr[13] = y; tr[14] = z;
		
		multiply_matrix(tr,matrix,matrix);
	}

	/**
	 *  EFFECTUE LA MULTIPLICATION MATRICIELLE DE matrix POUR EFFECTUER UNE ROTATION SUR L'AXE (x,y,z) D'UN ANGLE DE angle
	 * @param matrix
	 * @param x
	 * @param y
	 * @param z
	 * @param angle
	 */
	public static void rotate(float[] matrix, float x, float y, float z, float angle) {
		

		/* TODO PENSEZ A UTILISER LA FONCTION multiply_matrix() - TROIS FOIS ! */
		
		setIdentity(RX);
		setIdentity(RY);
		setIdentity(RZ);
		angle = (float) Math.toRadians(angle);
		
		if(x==1){
			RX[5] = (float) Math.cos(angle);
			RX[6] = - (float) Math.sin(angle);
			RX[9]= (float) Math.sin(angle);
			RX[10] = (float) Math.cos(angle);
		}
		
		if(y==1){
			RY[0]  = (float) Math.cos(angle);
			RY[2] = (float) Math.sin(angle);
			RY[8] = - (float) Math.sin(angle);
			RY[10] = (float) Math.cos(angle);
		}
		
		if(z==1){
			RZ[0] = (float) Math.cos(angle);
			RZ[1] = -(float) Math.sin(angle);
			RZ[4] = (float) Math.sin(angle);
			RZ[5] = (float) Math.cos(angle);
		}
		
		multiply_matrix(RX,matrix,matrix);
		multiply_matrix(RY,matrix,matrix);
		multiply_matrix(RZ,matrix,matrix);
	}

	/**
	 * FONCTION UTILITAIRE APPELANT scale(matrix, sX,sY,sZ);
	 * @param matrix
	 * @param scale
	 */
	public static void scale(float[] matrix, float scale) {
		scale(matrix,scale, scale, scale);
	}

	/**
	 * EFFECTUE LA MULTIPLICATION MATRICIELLE DE matrix POUR EFFECTUER UN FACTEUR D'ECHELLE DE (sX, sY, sZ) 
	 * @param matrix
	 * @param sX
	 * @param sY
	 * @param sZ
	 */
	public static void scale(float[] matrix, float sX, float sY, float sZ) {

		/* TODO PENSEZ A UTILISER LA FONCTION multiply_matrix() */
		
		setIdentity(sc);
		sc[0] = sX;
		sc[5] = sY;
		sc[10] = sZ;
		
		multiply_matrix(sc,matrix,matrix);
	}

	/*===============================================================================================================================
	 * GESTION DE LA PILE DE MATRICE 
	 *===============================================================================================================================
	 */
	
	static LinkedList<float []> matrixStack = new LinkedList<float[]>();
	
	/**
	 * DEPILE UNE MATRICE DE LA PILE DE MATRICE ET LA COPIE DANS LA MATRICE matrix
	 * @param matrix
	 * 
	 */
	public static void popMatrix(float[] matrix) {
		if (matrixStack.size()>0){		
			copy(matrixStack.remove(0),matrix);	
			indCourant --;
		}		
	}
	
	/**
	 * EMPILE UNE COPIE DE LA MATRICE matrix, MAIS NE MODIFIE PAS matrix
	 * 
	 * @param matrix
	 */
	public static void pushMatrix(float[]matrix){
		copy(matrix,pop[indCourant]);
		matrixStack.add(0,pop[indCourant]);
		indCourant ++;
	}
	
	public static void main(String [] args){
		float[] m1 = new float[16];
		float[] m2 = new float[16];
		
		setIdentity(m1);
		setIdentity(m2);
		m1[12] = m1[13] = m1[14] = 2;
		m2[12] = m2[13] = m2[14] = 2;
		
		System.out.println("M1 : ");
		System.out.print("[");
		for(int i = 0; i < 16; i ++){
			if(i != 0 && i % 4 == 0) System.out.println();
			System.out.print(m1[i]+", ");
		}
		System.out.println("]\nM2 : ");
		System.out.print("[");
		for(int i = 0; i < 16; i ++){
			if(i != 0 &&i % 4 == 0) System.out.println();
			System.out.print(m2[i]+", ");
			
		}
		
		multiply_matrix(m1,m2,m1);
		
		System.out.println("]\nMR : ");
		System.out.print("[");
		for(int i = 0; i < 16; i ++){
			if(i != 0 && i % 4 == 0) System.out.println();
			System.out.print(m1[i]+", ");
		}
		
	}
	
	
}
