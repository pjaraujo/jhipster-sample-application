export interface IShippingProcess {
  id?: number;
}

export class ShippingProcess implements IShippingProcess {
  constructor(public id?: number) {}
}

export function getShippingProcessIdentifier(shippingProcess: IShippingProcess): number | undefined {
  return shippingProcess.id;
}
