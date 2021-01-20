# Install script for directory: /home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic

# Set the install prefix
if(NOT DEFINED CMAKE_INSTALL_PREFIX)
  set(CMAKE_INSTALL_PREFIX "/usr/local")
endif()
string(REGEX REPLACE "/$" "" CMAKE_INSTALL_PREFIX "${CMAKE_INSTALL_PREFIX}")

# Set the install configuration name.
if(NOT DEFINED CMAKE_INSTALL_CONFIG_NAME)
  if(BUILD_TYPE)
    string(REGEX REPLACE "^[^A-Za-z0-9_]+" ""
           CMAKE_INSTALL_CONFIG_NAME "${BUILD_TYPE}")
  else()
    set(CMAKE_INSTALL_CONFIG_NAME "Release")
  endif()
  message(STATUS "Install configuration: \"${CMAKE_INSTALL_CONFIG_NAME}\"")
endif()

# Set the component getting installed.
if(NOT CMAKE_INSTALL_COMPONENT)
  if(COMPONENT)
    message(STATUS "Install component: \"${COMPONENT}\"")
    set(CMAKE_INSTALL_COMPONENT "${COMPONENT}")
  else()
    set(CMAKE_INSTALL_COMPONENT)
  endif()
endif()

# Install shared libraries without execute permission?
if(NOT DEFINED CMAKE_INSTALL_SO_NO_EXE)
  set(CMAKE_INSTALL_SO_NO_EXE "1")
endif()

# Is this installation the result of a crosscompile?
if(NOT DEFINED CMAKE_CROSSCOMPILING)
  set(CMAKE_CROSSCOMPILING "TRUE")
endif()

if("x${CMAKE_INSTALL_COMPONENT}x" STREQUAL "xUnspecifiedx" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/include/relic" TYPE FILE FILES
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_arch.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_bc.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_bench.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_bn.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_core.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_cp.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_dv.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_eb.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_ec.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_ed.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_ep.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_epx.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_err.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_fb.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_fbx.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_fp.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_fpx.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_label.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_md.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_pc.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_pool.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_pp.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_rand.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_test.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_trace.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_types.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/relic_util.h"
    )
endif()

if("x${CMAKE_INSTALL_COMPONENT}x" STREQUAL "xUnspecifiedx" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/include/relic/low" TYPE FILE FILES
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/low/relic_bn_low.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/low/relic_dv_low.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/low/relic_fb_low.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/low/relic_fp_low.h"
    "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/include/low/relic_fpx_low.h"
    )
endif()

if("x${CMAKE_INSTALL_COMPONENT}x" STREQUAL "xUnspecifiedx" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/include/relic" TYPE DIRECTORY FILES "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/.cxx/cmake/evonetRelease/arm64-v8a/dashj-bls/bls-signatures/contrib/relic/include/")
endif()

if("x${CMAKE_INSTALL_COMPONENT}x" STREQUAL "xUnspecifiedx" OR NOT CMAKE_INSTALL_COMPONENT)
  file(INSTALL DESTINATION "${CMAKE_INSTALL_PREFIX}/cmake" TYPE FILE FILES "/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/cpp/dashj-bls/bls-signatures/contrib/relic/cmake/relic-config.cmake")
endif()

if(NOT CMAKE_INSTALL_LOCAL_ONLY)
  # Include the install script for each subdirectory.
  include("/home/tops/AndroidStudioProjects/ChiragProject/Dash/Code/New/28/dashwallet_android/wallet/.cxx/cmake/evonetRelease/arm64-v8a/dashj-bls/bls-signatures/contrib/relic/src/cmake_install.cmake")

endif()

