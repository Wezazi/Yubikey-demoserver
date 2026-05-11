// Copyright (c) 2026, Yubico AB
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice, this
//    list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright notice,
//    this list of conditions and the following disclaimer in the documentation
//    and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
// DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
// FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
// DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
// SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
// CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
// OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package com.yubico.internal.util

import org.junit.runner.RunWith
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.junit.JUnitRunner
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

import scala.util.Failure
import scala.util.Try

@RunWith(classOf[JUnitRunner])
class ByteInputStreamSpec
    extends AnyFunSpec
    with Matchers
    with ScalaCheckDrivenPropertyChecks {

  describe("ByteInputStream.read(int)") {

    val genByteArray = Gen.listOfN(500, arbitrary[Byte]).map(_.toArray)

    it("preserves the input array.") {
      forAll(for {
        input <- genByteArray
        chunkLength <- Gen.choose(0, input.length)
      } yield (input, chunkLength)) {
        case (input, chunkLength) =>
          val s = new ByteInputStream(input)

          var buf = List.empty[Byte]
          while (buf.length < input.length) {
            buf =
              buf :++ s.read(Math.min(chunkLength, input.length - buf.length))
          }

          buf should equal(input)
      }
    }

    it("rejects negative length input.") {
      forAll(genByteArray, Gen.negNum[Int]) { (input, l) =>
        val s = new ByteInputStream(input)
        val result = Try(s.read(l))
        result shouldBe a[Failure[_]]
        result.failed.get shouldBe an[IllegalArgumentException]
      }
    }

    it("rejects length input greater than remaining data.") {
      forAll(genByteArray, Gen.posNum[Int]) { (input, dl) =>
        val s = new ByteInputStream(input)
        val result = Try(s.read(input.length + dl))
        result shouldBe a[Failure[_]]
        result.failed.get shouldBe an[IllegalArgumentException]
      }
    }

    it("rejects unreasonable length input.") {
      forAll(genByteArray) { input =>
        val s = new ByteInputStream(input)
        val result = Try(s.read(Int.MaxValue))
        result shouldBe a[Failure[_]]
        result.failed.get shouldBe an[IllegalArgumentException]
      }
    }
  }
}
