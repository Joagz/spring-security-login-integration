import type { NextFetchEvent, NextRequest } from "next/server";
import { NextResponse } from "next/server";

export function middleware(req: NextRequest, ev: NextFetchEvent) {
  const userDetails = req.cookies.get("userDetails");

  if (userDetails?.value && req.url.includes("/login")) {
    return NextResponse.redirect(new URL("/", req.url));
  } else if (userDetails?.value) {
    return NextResponse.next();
  } else if (!req.url.includes("/login")) {
    return NextResponse.redirect(new URL("/login", req.url));
  } else {
    return NextResponse.next();
  }
}

export const config = {
  matcher: ["/", "/login"],
};
