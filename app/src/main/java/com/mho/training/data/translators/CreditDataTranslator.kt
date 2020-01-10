package com.mho.training.data.translators

import com.example.android.domain.Credit
import com.example.android.framework.data.remote.models.credit.ServerCredit
import com.mho.training.utils.Constants

fun ServerCredit.toDomainCredit(): Credit =
    Credit(
        id,
        name,
        character,
        generateCreditProfilePath(profilePath)
    )

private fun generateCreditProfilePath(profilePath: String?) =
    if(profilePath.isNullOrBlank()) null else Constants.URL_IMAGE_TBMD + profilePath
