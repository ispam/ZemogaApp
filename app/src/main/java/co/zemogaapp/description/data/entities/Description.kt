package co.zemogaapp.description.data.entities

import android.os.Parcelable
import co.zemogaapp.common.constants.DESCRIPTION_TYPE
import co.zemogaapp.common.delegate.RecyclerViewType
import kotlinx.android.parcel.Parcelize

/**
 * Created by diego.urrea on 10/4/2020.
 */
@Parcelize
data class Description(val userId: Int,
                       val id: Int,
                       val title: String,
                       val body: String): RecyclerViewType, Parcelable {

    override fun getViewType(): Int = DESCRIPTION_TYPE
}
