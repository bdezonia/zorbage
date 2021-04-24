/*
 * Zorbage: an algebraic data hierarchy for use in numeric processing.
 *
 * Copyright (c) 2016-2021 Barry DeZonia All rights reserved.
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
package example;

import java.sql.Connection;

import nom.bdezonia.zorbage.algebra.G;
import nom.bdezonia.zorbage.datasource.ArrayDataSource;
import nom.bdezonia.zorbage.datasource.BigListDataSource;
import nom.bdezonia.zorbage.datasource.IndexedDataSource;
import nom.bdezonia.zorbage.datasource.ListDataSource;
import nom.bdezonia.zorbage.storage.array.ArrayStorage;
import nom.bdezonia.zorbage.storage.file.FileStorage;
import nom.bdezonia.zorbage.storage.jdbc.JdbcStorage;
import nom.bdezonia.zorbage.storage.sparse.SparseStorage;
import nom.bdezonia.zorbage.type.bool.BooleanMember;
import nom.bdezonia.zorbage.type.complex.float32.ComplexFloat32Member;
import nom.bdezonia.zorbage.type.integer.int32.SignedInt32Member;
import nom.bdezonia.zorbage.type.integer.int8.SignedInt8Member;

/**
 * @author Barry DeZonia
 */
class Storage {
	
	/*
	 *  Zorbage currently has four main types of data storage. Zorbage hides the implementation
	 *  details of data storage behind the indexedDataSource interface. Algorithms rely on
	 *  working through the IndexedDataSource to set and get values regardless of how they are
	 *  stored. Indeed they have no idea how the data is stored.
	 *  
	 *  Storage types are used to actually save bytes etc. to memory or disk or databases as
	 *  needed.
	 *  
	 *  Array storage is one kind of storage. This storage is quick to access but limited by
	 *  the RAM installed on the computer where Zorbage is running. Typical ArrayStorage
	 *  cannot exceed 2^31 elements.
	 *  
	 *  BigList is an array storage type that can exceed 2^31 elements. It loads everything
	 *  in RAM and list length can reach 2^62 elements. However this is all in RAM and thus it
	 *  is actually limited by how much RAM is allocated to the JVM. This amount is tunable and
	 *  you can find more information about this topic on the web.
	 *  
	 *  File storage is another type supported by Zorbage. Using file storage Zorbage can
	 *  allocate lists containing up to 2^63 elements. File storage uses a small amount of
	 *  buffer memory to allow pages of elements on disk to be pulled into RAM when they are
	 *  need. Accessing file storage is slower than accessing array storage but sometimes it
	 *  is the only way to do a large computation. Given the limitations of file based storage
	 *  algorithms that use multiple threads to work on multiple pieces of a list at the same
	 *  time will not work well. These algorithms might cause a lot of disk read/write access
	 *  which will be slower than using a single thread approach.
	 *  
	 *  Database storage is another type provided by Zorbage. Zorbage uses JDBC connections
	 *  to interface with external 3rd party databases. Temporary tables are used to store
	 *  data. One can use algorithms that require access to a database and while at it store
	 *  your own data in the database. One could conceivably do offline database analysis and
	 *  queries of the stored Zorbage data.
	 *  
	 *  Another RAM based storage type supported by Zorbage is sparse storage. This type of
	 *  storage is used to store big lists, vectors, matrices, and tensors completely in RAM.
	 *  Only nonzero values are stored. The number of total elements can reach 2^63 and
	 *  performance is proportional to the number of nonzero elements 
	 */
	
	// Let's show some examples:

	@SuppressWarnings("unused")
	void example1() {
		
		// allocate storage without worrying where or how it is stored
		
		IndexedDataSource<BooleanMember> list = nom.bdezonia.zorbage.storage.Storage.allocate(G.BOOL.construct(), 50000);
	}
	
	@SuppressWarnings("unused")
	void example2() {
		
		// allocate storage in RAM : preferred approach : elements stored in an array of primitives
		
		IndexedDataSource<BooleanMember> list = ArrayStorage.allocate(G.BOOL.construct(), 50000);
		
		// allocate storage in RAM : elements stored in an array of Objects
		
		list = ArrayDataSource.construct(G.BOOL, 1000);
		
		// allocate storage in RAM : elements stored in a linked list of Objects
		
		list = ListDataSource.construct(G.BOOL, 1000);
		
		// allocate storage in RAM : elements stored in a BigList of Objects
		
		list = new BigListDataSource<>(G.BOOL, 7500);
	}
	
	@SuppressWarnings("unused")
	void example3() {
		
		// allocate storage in a file
		
		IndexedDataSource<SignedInt8Member> list = FileStorage.allocate(G.INT8.construct(), 50000);
	}
	
	@SuppressWarnings("unused")
	void example4() {
		
		// allocate storage in a database table
		
		Connection dbConn = null; // Create a database connection to your database as needed.
		
		IndexedDataSource<SignedInt32Member> list = JdbcStorage.allocate(dbConn, G.INT32.construct(), 50000);
	}
	
	@SuppressWarnings("unused")
	void example5() {
		
		// allocate storage sparsely
		
		IndexedDataSource<ComplexFloat32Member> list = SparseStorage.allocate(G.CFLT.construct(), 50000);
	}
}
