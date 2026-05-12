package com.yubico.fido.metadata;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enumeration of valid PIN/UV auth protocol version identifiers.
 *
 * @see <a
 *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#pin-uv-auth-protocol">Client
 *     to Authenticator Protocol (CTAP) §6.5. authenticatorClientPIN (0x06)</a>
 */
public enum CtapPinUvAuthProtocolVersion {

  /**
   * (NOT DEFINED IN SPEC) Placeholder for any unknown {@link CtapPinUvAuthProtocolVersion} value.
   *
   * @since 2.9.0
   */
  @JsonEnumDefaultValue
  UNKNOWN(0),

  /**
   * Represents PIN/UV Auth Protocol One.
   *
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#pinProto1">Client
   *     to Authenticator Protocol (CTAP) §6.5.6. PIN/UV Auth Protocol One</a>
   */
  ONE(1),

  /**
   * Represents PIN/UV Auth Protocol Two.
   *
   * @see <a
   *     href="https://fidoalliance.org/specs/fido-v2.1-ps-20210615/fido-client-to-authenticator-protocol-v2.1-ps-20210615.html#pinProto2">Client
   *     to Authenticator Protocol (CTAP) §6.5.7. PIN/UV Auth Protocol Two</a>
   */
  TWO(2);

  @JsonValue private final int value;

  CtapPinUvAuthProtocolVersion(int value) {
    this.value = value;
  }
}
