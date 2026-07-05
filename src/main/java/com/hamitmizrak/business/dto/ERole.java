package com.hamitmizrak.business.dto;

// final verirsem;
// 1-) Değişkende: sabit değer
// 2-) Metotda: Override edilemez
// 3-) Classta: Kalıtımlanamaz, edilemez

import lombok.Getter;

// 1-) Değişkende final verirsek;
// 1-a_) Değeri sabit yapar.
// 2-a_) Sadece Get metodunu eklemeye yarar
// 3-a_) Bizi Constructur yazmamıza zorlar
@Getter
public enum ERole {
    ADMIN(1L,"admin") ,
    ACCOUNTING(2L,"accounting") ,
    WRITER(3L,"writer") ,
    USER(4L,"user");

    // variable
    private final Long key;
    private final String value;

    // Constructor (Parametreli constructor)
    // Constructor'a private verirsek;
    // Bu Enum'ın instance(new) oluşturulmasına izin vermiyor.
    private ERole(Long key, String value) {
        this.key = key;
        this.value = value;
    }

    // GETTER
    public Long getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
} //end Enum ERoles
