// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
////////////////////////////////////////////////////////////////////////////////

package com.google.crypto.tink;

import java.security.GeneralSecurityException;

/**
 * Interface for hybrid encryption.
 *
 * <p>Hybrid Encryption combines the efficiency of symmetric encryption with the convenience
 * of public-key encryption: to encrypt a message a fresh symmetric key is generated
 * and used to encrypt the actual plaintext data, while the recipient’s public key
 * is used to encrypt the symmetric key only, and the final ciphertext consists of
 * the symmetric ciphertext and the encrypted symmetric key. Note that Hybrid Encryption
 * does not provide authenticity of the message (the recipient does not know the identity
 * of the sender).
 *
 * <p>The functionality of Hybrid Encryption is represented as a pair of primitives
 * (interfaces): {@link HybridEncrypt} for encryption of data, and {@link HybridDecrypt}
 * for decryption. Implementations of these interfaces are secure against adaptive
 * chosen ciphertext attacks. In addition to {@code plaintext} the encryption takes
 * an extra parameter {@code contextInfo}, which usually is public data implicit
 * from the context, but should be bound to the resulting ciphertext, i.e. the ciphertext
 * allows for checking the integrity of {@code contextInfo} (but there are no guarantees
 * wrt. the secrecy or authenticity of {@code contextInfo}).
 *
 * <p>{@code contextInfo} can be empty or null, but to ensure the correct decryption
 * of the resulting ciphertext the same value must be provided for decryption operation
 * (cf. {@link HybridDecrypt}).
 *
 * <p>A concrete instantiation of this interface can implement the binding
 * of {@code contextInfo} to the ciphertext in various ways, for example:
 * <ul>
 *   <li>use {@code contextInfo} as "associated data"-input for the employed AEAD
 *       symmetric encryption (cf. https://tools.ietf.org/html/rfc5116). </li>
 *   <li>use {@code contextInfo} as "CtxInfo"-input for HKDF (if the implementation uses
 *       HKDF as key derivation function, cf. https://tools.ietf.org/html/rfc5869). </li>
 * </ul>
 */
public interface HybridEncrypt {
  /**
   * Encryption operation:
   * encrypts {@code plaintext} binding {@code contextInfo} to the resulting ciphertext.
   *
   * @return resulting ciphertext
   */
  byte[] encrypt(final byte[] plaintext, final byte[] contextInfo)
      throws GeneralSecurityException;
}
