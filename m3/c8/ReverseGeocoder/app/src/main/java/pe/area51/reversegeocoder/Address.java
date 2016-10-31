package pe.area51.reversegeocoder;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alumno on 8/23/16.
 */
public class Address implements Parcelable {

    final String longitude;
    final String latitude;
    final String displayName;
    final String city;
    final String country;

    public Address(String longitude, String latitude, String displayName, String city, String country) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.displayName = displayName;
        this.city = city;
        this.country = country;
    }

    protected Address(Parcel in) {
        longitude = in.readString();
        latitude = in.readString();
        displayName = in.readString();
        city = in.readString();
        country = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "Address{" +
                "longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", displayName='" + displayName + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (longitude != null ? !longitude.equals(address.longitude) : address.longitude != null)
            return false;
        if (latitude != null ? !latitude.equals(address.latitude) : address.latitude != null)
            return false;
        if (displayName != null ? !displayName.equals(address.displayName) : address.displayName != null)
            return false;
        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        return country != null ? country.equals(address.country) : address.country == null;

    }

    @Override
    public int hashCode() {
        int result = longitude != null ? longitude.hashCode() : 0;
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(longitude);
        dest.writeString(latitude);
        dest.writeString(displayName);
        dest.writeString(city);
        dest.writeString(country);
    }
}
