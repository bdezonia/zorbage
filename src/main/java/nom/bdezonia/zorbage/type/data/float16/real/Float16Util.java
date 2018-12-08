// BDZ Note 12-8-2018
// Java code found on the internet mostly unattributed.
// Now including the best guess at the original .CPP implementation's license

/*
OpenGLSamples/license.txt
    
Copyright 2011 NVIDIA Corporation

BY DOWNLOADING THE SOFTWARE AND OTHER AVAILABLE MATERIALS, YOU  ("DEVELOPER") AGREE TO
BE BOUND BY THE FOLLOWING TERMS AND CONDITIONS

The materials available for download to Developers may include software in both sample
source ("Source Code") and object code ("Object Code") versions, documentation 
("Documentation"), certain art work ("Art Assets") and other materials (collectively,
these materials referred to herein as "Materials").  Except as expressly indicated herein,
all terms and conditions of this Agreement apply to all of the Materials.

Except as expressly set forth herein, NVIDIA owns all of the Materials and makes them
available to Developer only under the terms and conditions set forth in this Agreement.

License:  Subject to the terms of this Agreement, NVIDIA hereby grants to Developer a
royalty-free, non-exclusive license to possess and to use the Materials.  The following
terms apply to the specified type of Material:

Source Code:  Developer shall have the right to modify and create derivative works with
the Source Code.  Developer shall own any derivative works ("Derivatives") it creates to
the Source Code, provided that Developer uses the Materials in accordance with the terms
of this Agreement.  Developer may distribute the Derivatives, provided that all NVIDIA
copyright notices and trademarks are used properly and the Derivatives include the
following statement: "This software contains source code provided by NVIDIA Corporation."  

Object Code:  Developer agrees not to disassemble, decompile or reverse engineer the Object
Code versions of any of the Materials.  Developer acknowledges that certain of the Materials
provided in Object Code version may contain third party components that may be subject to
restrictions, and expressly agrees not to attempt to modify or distribute such Materials
without first receiving consent from NVIDIA.

Art Assets:  Developer shall have the right to modify and create Derivatives of the Art
Assets, but may not distribute any of the Art Assets or Derivatives created therefrom
without NVIDIA’s prior written consent.

Government End Users: If you are acquiring the Software on behalf of any unit or agency of
the United States Government, the following provisions apply. The Government agrees the
Software and documentation were developed at private expense and are provided with
“RESTRICTED RIGHTS”. Use, duplication, or disclosure by the Government is subject to
restrictions as set forth in DFARS 227.7202-1(a) and 227.7202-3(a) (1995),
DFARS 252.227-7013(c)(1)(ii) (Oct 1988), FAR 12.212(a)(1995), FAR 52.227-19, (June 1987)
or FAR 52.227-14(ALT III) (June 1987),as amended from time to time. In the event that this
License, or any part thereof, is deemed inconsistent with the minimum rights identified in
the Restricted Rights provisions, the minimum rights shall prevail.

No Other License. No rights or licenses are granted by NVIDIA under this License, expressly
or by implication, with respect to any proprietary information or patent, copyright, trade
secret or other intellectual property right owned or controlled by NVIDIA, except as
expressly provided in this License.

Term:  This License is effective until terminated.  NVIDIA may terminate this Agreement
(and with it, all of Developer’s right to the Materials) immediately upon written notice
(which may include email) to Developer, with or without cause.

Support:  NVIDIA has no obligation to support or to continue providing or updating any
of the Materials.

No Warranty:  THE SOFTWARE AND ANY OTHER MATERIALS PROVIDED BY NVIDIA TO DEVELOPER
HEREUNDER ARE PROVIDED "AS IS."  NVIDIA DISCLAIMS ALL WARRANTIES, EXPRESS, IMPLIED OR
STATUTORY, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF TITLE, MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.

LIMITATION OF LIABILITY:  NVIDIA SHALL NOT BE LIABLE TO DEVELOPER, DEVELOPER’S CUSTOMERS,
OR ANY OTHER PERSON OR ENTITY CLAIMING THROUGH OR UNDER DEVELOPER FOR ANY LOSS OF PROFITS,
INCOME, SAVINGS, OR ANY OTHER CONSEQUENTIAL, INCIDENTAL, SPECIAL, PUNITIVE, DIRECT OR
INDIRECT DAMAGES (WHETHER IN AN ACTION IN CONTRACT, TORT OR BASED ON A WARRANTY), EVEN IF
NVIDIA HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.  THESE LIMITATIONS SHALL APPLY
NOTWITHSTANDING ANY FAILURE OF THE ESSENTIAL PURPOSE OF ANY LIMITED REMEDY.  IN NO EVENT
SHALL NVIDIA’S AGGREGATE LIABILITY TO DEVELOPER OR ANY OTHER PERSON OR ENTITY CLAIMING
THROUGH OR UNDER DEVELOPER EXCEED THE AMOUNT OF MONEY ACTUALLY PAID BY DEVELOPER TO NVIDIA
FOR THE SOFTWARE OR ANY OTHER MATERIALS.

*/

package nom.bdezonia.zorbage.type.data.float16.real;

/**
 * 
 * @author Unknown
 *
 */
public final class Float16Util {

	/**
	 * port of nvidias gameworks sample
	 * https://github.com/NVIDIAGameWorks/OpenGLSamples/blob/master/samples/es3-kepler/HDR/HDRImages.h
	 *
	 * @version        1.0, 2015.03.05
	 * @author         nvidia, bas
	 */

	//~--- static fields --------------------------------------------------------

	/** -15 stored using a single precision bias of 127 */
	public final static int HALF_FLOAT_MIN_BIASED_EXP_AS_SINGLE_FP_EXP = 0x38000000;

	/** max exponent value in single precision that will be converted to Inf or Nan when stored as a half-float */
	public final static int HALF_FLOAT_MAX_BIASED_EXP_AS_SINGLE_FP_EXP = 0x47800000;

	/** 255 is the max exponent biased value */
	public final static int FLOAT_MAX_BIASED_EXP                       = ( 0xFF<<23 );
	public final static int HALF_FLOAT_MAX_BIASED_EXP                  = ( 0x1F<<10 );

	//~--- methods --------------------------------------------------------------

	///**
	// * @return float
	// */
	//public static float overflow()
	//{
	//  float f = 1.0e10f;
	//
	//  for(int i = 0; i < 10; i++)
	//   f *= f;  // this will overflow before the for loop terminates
	//
	//  return f;
	//}

	/**
	 * @param hf short
	 * @return float
	 */
	public static float convertHFloatToFloat(final short hf)
	{
		return Float.intBitsToFloat(convertHFloatToFloatBits(hf));
	}

	/**
	 * @param f float
	 * @return short
	 */
	public static short convertFloatToHFloat(final float f)
	{
		return convertFloatBitsToHFloat(Float.floatToRawIntBits(f));
	}

	/**
	 * @param hf short
	 * @return int
	 */
	public static int convertHFloatToFloatBits(final short hf)
	{
		final int sign     = ( hf>>15 );
		int       mantissa = ( hf & (( 1<<10 ) - 1 ));
		int       exp      = ( hf & HALF_FLOAT_MAX_BIASED_EXP );
		final int f;

		if(exp == HALF_FLOAT_MAX_BIASED_EXP)
		{
			/**
			 * we have a half-float NaN or Inf
			 * half-float NaNs will be converted to a single precision NaN
			 * half-float Infs will be converted to a single precision Inf
			 */
			exp = FLOAT_MAX_BIASED_EXP;

			if(mantissa != 0) mantissa = ( 1<<23 ) - 1;  // set all bits to indicate a NaN
		}
		else if(exp == 0x0)
		{

			/** convert half-float zero/denorm to single precision value */
			if(mantissa != 0)
			{
				mantissa <<= 1;
				exp      = HALF_FLOAT_MIN_BIASED_EXP_AS_SINGLE_FP_EXP;

				/** check for leading 1 in denorm mantissa */
				while(( mantissa & ( 1<<10 )) == 0)
				{

					/** for every leading 0, decrement single precision exponent by 1 and shift half-float mantissa value to the left */
					mantissa <<= 1;
					exp      -= ( 1<<23 );
				}

				/** clamp the mantissa to 10-bits */
				mantissa &= (( 1<<10 ) - 1 );

				/** shift left to generate single-precision mantissa of 23-bits */
				mantissa <<= 13;
			}
		}
		else
		{

			/** shift left to generate single-precision mantissa of 23-bits */
			mantissa <<= 13;

			/** generate single precision biased exponent value */
			exp      = ( exp<<13 ) + HALF_FLOAT_MIN_BIASED_EXP_AS_SINGLE_FP_EXP;
		}

		f = ( sign<<31 ) | exp | mantissa;

		return f;
	}

	/**
	 * @param floatBits int
	 * @return short
	 */
	//@SuppressWarnings ( "NumericCastThatLosesPrecision" )
	public static short convertFloatBitsToHFloat(final int floatBits)
	{
		final int s = ( floatBits>>16 ) & 0x00008000;
		int       e = (( floatBits>>23 ) & 0x000000ff ) - ( 127 - 15 );
		int       m = floatBits & 0x007fffff;

		/** Now reassemble s, e and m into a half: */
		if(e <= 0)
		{
			if(e < -10)
			{

				/**
				 * E is less than -10. The absolute value of f is less than HALF_MIN (f may be a small normalized float, a denormalized float or a zero).
				 *
				 * We convert f to a half zero with the same sign as f.
				 */
				return(short)s;
			}

			/**
			 * E is between -10 and 0. F is a normalized float
			 * whose magnitude is less than HALF_NRM_MIN.
			 *
			 * We convert f to a denormalized half.
			 *
			 *
			 * Add an explicit leading 1 to the significand.
			 */
			m |= 0x00800000;

			/**
			 * Round to m to the nearest (10+e)-bit value (with e between
			 * -10 and 0); in case of a tie, round to the nearest even value.
			 *
			 * Rounding may cause the significand to overflow and make
			 * our number normalized. Because of the way a half's bits
			 * are laid out, we don't have to treat this case separately;
			 * the code below will handle it correctly.
			 */
			final int t = 14 - e;
			final int a = ( 1<<( t - 1 )) - 1;
			final int b = ( m>>t ) & 1;

			m = ( m + a + b )>>t;

			/** Assemble the half from s, e (zero) and m. */
			final int r = s | m;

			return(short)r;
		}
		else if(e == 0xff - ( 127 - 15 ))
		{
			if(m == 0)
			{

				/**
				 * F is an infinity; convert f to a half infinity with the same sign as f.
				 */
				final int r = s | 0x7c00;

				return(short)r;
			}
			else
			{

				/**
				 * F is a NAN; we produce a half NAN that preserves
				 * the sign bit and the 10 leftmost bits of the
				 * significand of f, with one exception: If the 10
				 * leftmost bits are all zero, the NAN would turn
				 * into an infinity, so we have to set at least one
				 * bit in the significand.
				 */
				m >>= 13;

				final int r = s | 0x7c00 | m | (( m == 0 ) ? 1 : 0 );

				return(short)r;
			}
		}
		else
		{

			/**
			 * E is greater than zero. F is a normalized float.
			 * We try to convert f to a normalized half.
			 *
			 * Round to m to the nearest 10-bit value. In case of
			 * a tie, round to the nearest even value.
			 */
			m += 0x00000fff + (( m>>13 ) & 1 );

			if(( m & 0x00800000 ) != 0)
			{
				m = 0;                     // overflow in significand,
				e += 1;                    // adjust exponent
			}

			/** Handle exponent overflow */
			if(e > 30)
			{
				//overflow();                // Cause a hardware floating point overflow;

				final int r = s | 0x7c00;  // if this returns, the half becomes an infinity with the same sign as f.

				return(short)r;
			}

			/** Assemble the half from s, e and m. */
			final int r = s | ( e<<10 ) | ( m>>13 );

			return(short)r;
		}
	}

}
