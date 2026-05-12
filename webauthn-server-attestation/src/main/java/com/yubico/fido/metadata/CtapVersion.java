package com.yubico.fido.metadata;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

/**
 * Enumeration of CTAP versions.
 *
 * @see <a
 *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
 *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
 */
public enum CtapVersion {

  /**
   * (NOT DEFINED IN SPEC) Placeholder for any unknown {@link CtapVersion} value.
   *
   * @since 2.9.0
   */
  @JsonEnumDefaultValue
  @JsonAlias("FIDO_2_2") // Forbidden by CTAP 2.3 spec
  UNKNOWN,

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  U2F_V2,

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  FIDO_2_0,

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  FIDO_2_1_PRE,

  /**
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  FIDO_2_1,

  /**
   * @since 2.9.0
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.3-ps-20260226/fido-client-to-authenticator-protocol-v2.3-ps-20260226.html#authenticatorGetInfo">Client
   *     to Authenticator Protocol (CTAP) §6.4. authenticatorGetInfo (0x04)</a>
   */
  FIDO_2_3;
}
