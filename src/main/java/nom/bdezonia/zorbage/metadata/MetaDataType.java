/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2022 Barry DeZonia All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 
 * Neither the name of the <copyright holder> nor the names of its contributors may
 * be used to endorse or promote products derived from this software without specific
 * prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package nom.bdezonia.zorbage.metadata;

/**
 * The types of primitive data allowed in the metadata store.
 * 
 * @author Barry DeZonia
 */
public enum MetaDataType {

	BLOB,
	BLOB_ARRAY,
	BLOB_MATRIX,
	BYTE,
	BYTE_ARRAY,
	BYTE_MATRIX,
	SHORT,
	SHORT_ARRAY,
	SHORT_MATRIX,
	INT,
	INT_ARRAY,
	INT_MATRIX,
	LONG,
	LONG_ARRAY,
	LONG_MATRIX,
	FLOAT,
	FLOAT_ARRAY,
	FLOAT_MATRIX,
	DOUBLE,
	DOUBLE_ARRAY,
	DOUBLE_MATRIX,
	STRING,
	STRING_ARRAY,
	STRING_MATRIX,
	CHAR,
	CHAR_ARRAY,
	CHAR_MATRIX,
	BOOLEAN,
	BOOLEAN_ARRAY,
	BOOLEAN_MATRIX,
	BIGINTEGER,
	BIGINTEGER_ARRAY,
	BIGINTEGER_MATRIX,
	BIGDECIMAL,
	BIGDECIMAL_ARRAY,
	BIGDECIMAL_MATRIX,
	BLOB_MATRIX3D,
	BYTE_MATRIX3D,
	SHORT_MATRIX3D,
	INT_MATRIX3D,
	LONG_MATRIX3D,
	FLOAT_MATRIX3D,
	DOUBLE_MATRIX3D,
	STRING_MATRIX3D,
	CHAR_MATRIX3D,
	BOOLEAN_MATRIX3D,
	BIGINTEGER_MATRIX3D,
	BIGDECIMAL_MATRIX3D
}
