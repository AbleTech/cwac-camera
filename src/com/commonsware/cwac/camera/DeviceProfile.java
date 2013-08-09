/***
  Copyright (c) 2013 CommonsWare, LLC
  
  Licensed under the Apache License, Version 2.0 (the "License"); you may
  not use this file except in compliance with the License. You may obtain
  a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package com.commonsware.cwac.camera;

import android.os.Build;
import android.util.Log;

public class DeviceProfile {
  private static volatile DeviceProfile SINGLETON=null;

  synchronized public static DeviceProfile getInstance() {
    Log.d("DeviceProfile", Build.PRODUCT);
    
    if (SINGLETON == null) {
      if ("occam".equals(Build.PRODUCT)) {
        SINGLETON=new Nexus4DeviceProfile();
      }
      else if ("espressowifiue".equals(Build.PRODUCT)) {
        SINGLETON=new SamsungGalaxyTab2Profile();
      }
      else if ("samsung".equalsIgnoreCase(Build.MANUFACTURER)) {
        SINGLETON=new SamsungDeviceProfile();
      }
      else if ("motorola".equalsIgnoreCase(Build.MANUFACTURER)) {
        SINGLETON=new MotorolaDeviceProfile();
      }
      else {
        SINGLETON=new DeviceProfile();
      }
    }

    return(SINGLETON);
  }

  public DeviceProfile() {
  }

  public boolean useTextureView() {
    return(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN);
  }

  public boolean encodesRotationToExif() {
    return(false);
  }

  public boolean rotateBasedOnExif() {
    return(false);
  }

  public int getMaxPictureHeight() {
    return(Integer.MAX_VALUE);
  }

  private static class Nexus4DeviceProfile extends DeviceProfile {
    public int getMaxPictureHeight() {
      return(720);
    }
  }

  private static class SamsungGalaxyTab2Profile extends DeviceProfile {
    public int getMaxPictureHeight() {
      return(1104);
    }
  }

  private static class FullExifFixupDeviceProfile extends DeviceProfile {
    @Override
    public boolean encodesRotationToExif() {
      return(true);
    }

    @Override
    public boolean rotateBasedOnExif() {
      return(true);
    }
  }

  private static class SamsungDeviceProfile extends
      FullExifFixupDeviceProfile {
  }

  private static class MotorolaDeviceProfile extends
      FullExifFixupDeviceProfile {
  }
}
